# PhishingApp

Android標準ブラウザ利用者を標的としたフィッシングアプリが作成可能なことを示す実証アプリです。

「ブラウザの履歴・ブックマークの読み取り」という権限を持つアプリであれば、Android用セキュリティアプリの多くが持っている「セーフブラウジング機能」と同様の手法を取ることにより、標準ブラウザで現在閲覧中のURLを取得することが可能で、ある特定のURLへのアクセスをフックし、フィッシングアプリが用意した画面を表示することが可能なのです。また、Intentを用いればインターネットアクセスのパーミッションが無くてもユーザIDやパスワードを送信することが可能であることにも注意が必要です。

詳しくは、[こちらのページ](http://blog.virifi.net/blog/2012/11/06/phishing-app-targeting-stock-browser-users/)を御覧ください。

### ダウンロード

* [PhshingApp.apk](https://github.com/downloads/virifi/Android-Stock-Browser-Phishing-App/PhishingApp.apk) 


### パーミッション

* com.android.browser.permission.READ_HISTORY_BOOKMARKS

### スクリーンショット

![スクリーンショット](https://raw.github.com/virifi/PlayAppInstaller/master/readme_imgs/screenshot1-small.png)

### ライセンス

```
 Copyright (C) 2012 virifi 

 Licensed under the Apache License, Version 2.0 (the "License"); 
 you may not use this file except in compliance with the License. 
 You may obtain a copy of the License at
 
     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software 
 distributed under the License is distributed on an "AS IS" BASIS, 
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 See the License for the specific language governing permissions and 
 limitations under the License. 
 ```
