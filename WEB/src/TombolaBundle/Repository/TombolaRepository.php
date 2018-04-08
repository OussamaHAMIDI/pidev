<?php

/**
 * Created by PhpStorm.
 * User: Hamdi Megdiche
 * Date: 28/03/2018
 * Time: 21:40
 */

namespace TombolaBundle\Repository;
use Doctrine\ORM\EntityRepository;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;



class TombolaRepository extends EntityRepository
{
    public function rechercheAction($word)
    {
        $qb= $this->getEntityManager()->createQueryBuilder();
        $qb->select('p')
            ->from('TombolaBundle:Tombola', 'p')
            ->where('p.titre LIKE :word')
            ->setParameter('word', '%'.$word.'%');

        $q = $qb->getQuery();
        return $q->execute();

    }

    public function insererGagnant($id_tombola,$id_gagnant){
        $query=$this->getEntityManager()
            ->createQuery('UPDATE TombolaBundle:Tombola t SET t.id_gagnant = : nv_id WHERE t.id = : id');
        $query->setParameter('nv_id',$id_gagnant);
        $query->setParameter('id',$id_tombola);
        $query->execute();
    }

    
    public function recherchebytitreAction($word)

    {
        $qb= $this->getEntityManager()->createQueryBuilder();
        $qb->select('p')
            ->from('TombolaBundle:Tombola', 'p')
            ->where('p.titre LIKE :word')
            ->setParameter('word', '%'.$word.'%');
        $q = $qb->getQuery();
        return $q->execute();
    }
    public function moisTombolaAction($mois,$id_artisan){
        $qb = $this->getEntityManager()->createQueryBuilder();
        $result = $this->getEntityManager()->createQuery('SELECT t FROM TombolaBundle:Tombola t WHERE 
        MONTH(t.dateAjout) = :m and t.idArtisan = :id')
            ->setParameter('m',$mois) ->setParameter('id',$id_artisan)
            ->getResult();
        return  $result;

    }

}