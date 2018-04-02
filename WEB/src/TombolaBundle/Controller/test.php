<?php
/**
 * Created by PhpStorm.
 * User: Hamdi
 * Date: 02/04/2018
 * Time: 17:01
 */
if (password_verify("test","$2a$04\$uzc1a6f.mvKxXF/Jw6otK.0ZiCX904HpmOdLlN5FiRdEphD/sSpCO")) {
    echo 'Password is correct';
}else
    echo 'Password not correct';