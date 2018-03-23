
# Installation WEB

## 1. Cloner le projet sous le répertoire `C:\xampp\htdocs\`
Home page est accessible à cette addresse : http://localhost/pidev/WEB/web/app_dev.php/


## 2. Télécharger les vendors
Avec Composer  :

    php composer.phar install

## 3. Publiez les assets
Publiez les assets dans le répertoire web :

    php bin/console assets:install web

