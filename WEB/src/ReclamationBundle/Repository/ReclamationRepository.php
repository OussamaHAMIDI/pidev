<?php

namespace ReclamationBundle\Repository;
use Doctrine\ORM\EntityRepository;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;


class ReclamationRepository extends EntityRepository
{
    public function rechercheAction($word)

    {
        $qb= $this->getEntityManager()->createQueryBuilder();
        $qb->select('r')
            ->from('ReclamationBundle:Reclamation', 'r')
            ->where('r.description LIKE :word')
            ->setParameter('word', '%'.$word.'%');
        $q = $qb->getQuery();
        return $q->execute();
    }

    public function insererReclamation($id_reclamation,$id_user,$description,$id_produit){
        $query=$this->getEntityManager()
            ->createQuery('INSERT INTO ReclamationBundle:Reclamation r SET r.id = : id_reclamation');
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
}