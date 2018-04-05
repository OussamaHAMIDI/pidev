<?php

namespace TombolaBundle\Repository;

use Doctrine\ORM\EntityRepository;

class TombolaParticipantsRepository extends EntityRepository
{
    public function Random_tombolaAction($id_tombola){

        $count = $this->createQueryBuilder('t')
            ->select('COUNT(t)')
            ->where('t.idTombola = :idTombola')
            ->setParameter('idTombola', $id_tombola)
            ->getQuery()
            ->getSingleScalarResult();

        $gagnant=$this->createQueryBuilder('t')
            ->setFirstResult(rand(0, $count - 1))
            ->setMaxResults(1)
            ->getQuery()
            ->getSingleResult();

        return $gagnant;
    }

    public function rowParticipant($id_tombola){
        $query=$this->getEntityManager()
            ->createQuery("SELECT COUNT(t) FROM  EshopUserBundle:InscriTombola t WHERE t.idTombola = :idTombola");
        $query->setParameter('idTombola',$id_tombola);
        return  $query->getSingleResult();
    }

    public function infoParticipant($id_tombola){
        $query=$this->getEntityManager()
            ->createQuery("SELECT i FROM  EshopUserBundle:InscriTombola i WHERE i.idTombola = :idTombola");
        $query->setParameter('idTombola',$id_tombola);
        return  $query->getResult();
    }

    public function getEmailfromId($id){
        $query=$this->getEntityManager()
            ->createQuery("SELECT u FROM  EshopUserBundle:User u WHERE u.id = :id_user");
        $query->setParameter('id_user',$id);
        return  $query->getResult();
    }

    public function dejaParticiperTombola($id_participant){
        $query=$this->getEntityManager()
            ->createQuery("SELECT COUNT(t) FROM  EshopUserBundle:InscriTombola t WHERE t.idGagnant = :id_participant");
        $query->setParameter('id_participant',$id_participant);
        return  $query->getSingleResult();

    }

}