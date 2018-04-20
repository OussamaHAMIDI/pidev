<?php

/**
 * Created by PhpStorm.
 * User: Montassar Laribi
 * Date: 28/03/2018
 * Time: 21:40
 */

namespace PanierRepository\Repository;

use Doctrine\ORM\EntityRepository;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;



class PanierRepository extends EntityRepository
{

    public function nextId()
    {
        $query=$this->getEntityManager()
            ->createQuery('SHOW TABLE STATUS LIKE \'PANIER\'');

       return $query->getResult();

    }

    public function test()
    {
        return true;
    }
}