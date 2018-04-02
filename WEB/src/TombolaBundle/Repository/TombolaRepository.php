<?php

/**
 * Created by PhpStorm.
 * User: Oumayma Gader
 * Date: 28/03/2017
 * Time: 21:40
 */

namespace GestionTombolaBundle\Repository;
use Doctrine\ORM\EntityRepository;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;


class TombolaRepository extends EntityRepository
{
    public function rechercheAction($word)

    {
        $qb= $this->getEntityManager()->createQueryBuilder();
        $qb->select('p')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('p.titre LIKE :word')
            ->setParameter('word', '%'.$word.'%');

        $q = $qb->getQuery();
        return $q->execute();

    }

    public function insererGagnant($id_tombola,$id_gagnant){
        $query=$this->getEntityManager()
            ->createQuery('UPDATE EshopUserBundle:Tombola t SET t.idGagnant = : nv_id WHERE t.idTombola = : id');
        $query->setParameter('nv_id',$id_gagnant);
        $query->setParameter('id',$id_tombola);
        $query->execute();
    }

    public function rowTombola(){
        $query=$this->getEntityManager()
            ->createQuery("SELECT COUNT(t) FROM  EshopUserBundle:Tombola t WHERE t.idTombola >'0'");
       // $query->setParameter('id',0);
        return  $query->getSingleResult();

    }

    
    public function recherchebytitreAction($word)

    {
        $qb= $this->getEntityManager()->createQueryBuilder();
        $qb->select('p')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('p.titre LIKE :word')
            ->setParameter('word', '%'.$word.'%');
        $q = $qb->getQuery();
        return $q->execute();

    }
    public function janvierTombolaAction(){
        /*$qb= $this->getEntityManager()
            ->createQuery("SELECT COUNT(t) FROM EshopUserBundle:Tombola t WHERE MONTH(p.dateAjout) = :02");
        return  $qb->getSingleResult();
        */
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','01')
            ->getQuery()
            ->getResult();
        return  $result;

    }
    public function fevrierTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','02')
            ->getQuery()
            ->getResult();
        return  $result;
        }
    public function marsTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','03')
            ->getQuery()
            ->getResult();
        return  $result;
        }
    public function avrilTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','04')
            ->getQuery()
            ->getResult();
        return  $result;
        }
    public function maiTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','05')
            ->getQuery()
            ->getResult();
        return  $result;
        }
    public function juinTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','06')
            ->getQuery()
            ->getResult();
        return  $result;
        }
    public function juilletTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','07')
            ->getQuery()
            ->getResult();
        return  $result;
        }
    public function aoutTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','08')
            ->getQuery()
            ->getResult();
        return  $result;
        }
    public function septembreTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','09')
            ->getQuery()
            ->getResult();
        return  $result;
        }
    public function octobreTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','10')
            ->getQuery()
            ->getResult();
        return  $result;
        }
    public function novembreTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','11')
            ->getQuery()
            ->getResult();
        return  $result;
        }

    public function decembreTombolaAction(){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $qb->select('COUNT(p)')
            ->from('EshopUserBundle:Tombola', 'p')
            ->where('MONTH(p.dateAjout) = :month')
            ->setParameter('month','12')
            ->getQuery()
            ->getResult();
        return  $result;
    }
}