<?php

namespace TombolaBundle\Repository;

use Doctrine\ORM\EntityRepository;
use UserBundle\Entity\User;

class TombolaParticipantsRepository extends EntityRepository
{
    public function Random_tombolaAction($id_tombola){

        $count = $this->createQueryBuilder('t')
            ->select('COUNT(t)')
            ->where('t.id = :id')
            ->setParameter('id', $id_tombola)
            ->getQuery()
            ->getSingleScalarResult();

        $gagnant=$this->createQueryBuilder('t')
            ->setFirstResult(rand(0, $count - 1))
            ->setMaxResults(1)
            ->getQuery()
            ->getSingleResult();

        return $gagnant;
    }
    

    public function infoParticipant($id_tombola){

            $query=$this->getEntityManager()
                ->createQuery("select (u.id)as id, (u.nom) as nom, (u.prenom) as prenom, (t.dateInscri) as dateIscription from UserBundle:User u,
          TombolaBundle:TombolaParticipants t 
            WHERE t.idParticipant IN (SELECT IDENTITY(p.idParticipant) 
                FROM TombolaBundle:TombolaParticipants p
              WHERE p.idTombola = :id)
            
            and t.idParticipant = u.id and t.idTombola = :id");
            $query->setParameter('id',$id_tombola);
        return $query->getResult();

    }


    public function dejaParticiperTombola($id_participant){
        $query=$this->getEntityManager()
            ->createQuery("SELECT t FROM  TombolaBundle:TombolaParticipants t WHERE t.idParticipant = :id_participant");
        $query->setParameter('id_participant',$id_participant);
        return  $query->getResult();

    }

}