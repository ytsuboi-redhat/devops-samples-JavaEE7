# 160602-cicd-sample/ansible/README.md

## 0. 事前準備
### インベントリファイルの設定
hosts ファイル (インベントリ) 中に JBoss EAP, PostgreSQL, Jenkins インストール対象サーバのアドレスを設定する。

※ 特に事情が無ければ全て同じサーバとして問題なし。

###### ファイル設定例

```
[eapservers]
192.168.140.137
[dbservers]
192.168.140.137
[jenkinsservers]
192.168.140.137
```

### JBoss EAP アーカイブファイルの配置

以下のリンクから JBoss EAP 7.0.0.GA の ZIP ファイルを入手し、roles/jboss-eap/copy/files ディレクトリに同 ZIP ファイルを配置する。
https://access.redhat.com/jbossnetwork/restricted/softwareDetail.html?softwareId=43891&product=appplatform&version=7.0&downloadType=distributions

## 1. 疎通確認

以下のコマンドにより ping/setup を実行する。

__$REMOTE_USERNAME__ には SSH 接続ユーザ名を指定する。

```
$ ansible eapservers -i hosts -m ping -u $REMOTE_USERNAME -k
SSH password:
192.168.140.137 | success >> {
    "changed": false,
    "ping": "pong"
}

$ ansible eapservers -i hosts -m setup -$REMOTE_USERNAME -k
SSH password:
192.168.140.137 | success >> {
    "ansible_facts": {
        "ansible_all_ipv4_addresses": [
            "192.168.140.137"
        ],
        "ansible_all_ipv6_addresses": [
            "fe80::20c:29ff:fe08:97e6"
        ],
        "ansible_architecture": "x86_64",
        "ansible_bios_date": "07/31/2013",
        "ansible_bios_version": "6.00",
        "ansible_cmdline": {
            "BOOT_IMAGE": "/vmlinuz-3.10.0-229.7.2.el7.x86_64",
            "LANG": "en_US.UTF-8",
            "crashkernel": "auto",
            "quiet": true,
            "rd.lvm.lv": "rhel/root",
            "rhgb": true,
            "ro": true,
            "root": "/dev/mapper/rhel-root",
            "systemd.debug": true
        },
...
        "ansible_swapfree_mb": 2047,
        "ansible_swaptotal_mb": 2047,
        "ansible_system": "Linux",
        "ansible_system_vendor": "VMware, Inc.",
        "ansible_user_dir": "/home/yoshikazu",
        "ansible_user_gecos": "Yoshikazu YAMADA",
        "ansible_user_gid": 1000,
        "ansible_user_id": "yoshikazu",
        "ansible_user_shell": "/bin/bash",
        "ansible_user_uid": 1000,
        "ansible_userspace_architecture": "x86_64",
        "ansible_userspace_bits": "64",
        "ansible_virtualization_role": "guest",
        "ansible_virtualization_type": "VMware",
        "module_setup": true
    },
    "changed": false
}


```

## 2. Jenkins サーバの構築と EAP サーバにバイナリを配置
以下のコマンドにより環境設定を行います。

__$REMOTE_USERNAME__ には SSH 接続ユーザ名を指定する。

```
ansible-playbook site.yml -i hosts -b -u $REMOTE_USERNAME -k -K
```
 - selinux を permissive　に
 - firewalld を 無効化
 - rootのSSH秘密鍵と公開鍵を配置
 - Git のインストール
 - Open JDK のインストール (JAVA\_HOME 環境変数設定 含む)
 - Maven のインストール (MAVEN\_HOME 環境変数設定 含む)
 - Jeknins のインストール (待ち受けポートおよび起動ユーザの変更 / plugin導入 / ビルドパイプライン作成 含む)
 - ansible のインストール
 - jmeter のインストール (テスト計画 / ダミーデータ 含む)
 - JBoss EAP アーカイブの配置（JBoss EAP インストール対象サーバ）
  - 本来は環境構築時にネットワークから取得することが望ましい

###### 補足

 - SSH 接続ユーザがパスワード無で sudo 可能な場合 -K オプションは不要
 - SSH 接続 にパスワード不要の場合 -k オプションは不要
 - --private-key オプションを使用して SSH キーを指定

###### 実行例
TODO
```  
$ ansible-playbook site.yml -i hosts -b -u yoshikazu -k -K
SSH password:
SUDO password[defaults to SSH password]:

PLAY [jenkinsservers] *********************************************************

TASK [setup] ******************************************************************
ok: [192.168.140.137]

TASK: [selinux/permissive | install libselinux-python] ************************
ok: [192.168.140.137]

....

TASK: [jboss-eap/copy : copy JBoss EAP archive] ******************************* 	
ok: [192.168.140.137]

PLAY RECAP ********************************************************************
192.168.140.137            : ok=38   changed=13    unreachable=0    failed=0   
```

## 3. Jenkins のビルドパイプラインビューからビルドパイプラインを実行

1. `http://<Jenkins サーバのホスト>:28080` にアクセスする。

1. 右フレームに表示されているテーブルの "Build pipeline" タブを選択し、ビルドパイプライン画面を表示する。

1. "Run" ボタンをクリックしてビルドパイプラインを起動する。
 - 起動後、ビルドパイプラインが表示されない（表示が更新されない）場合は、リロード等を行って再描画する
 - 初回起動時は "build" および "integration_test" ジョブに時間がかかるが、これは maven が初回ビルド時に依存ライブラリをすべて取得していることに起因している

ビルドパイプラインとして Jenkins に設定されている各ジョブの役割は以下の通り。
- build
 - ソースコードの取得
 - ソースコードのビルド
 - コンポーネントテスト
- setup_staging
 - ミドルウェアのセットアップと起動（ステージング環境）
- integration_test
 - インテグレーションテスト（ステージング環境）
- deploy_staging
 - アプリケーションのデプロイ（ステージング環境）
- setup_production
 - ミドルウェアのセットアップと起動（プロダクション環境）
- deploy_production
 - アプリケーションのデプロイ（プロダクション環境）
- performance_test
 - パフォーマンステスト（プロダクション環境）
