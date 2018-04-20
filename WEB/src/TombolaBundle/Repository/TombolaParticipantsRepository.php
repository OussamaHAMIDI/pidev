<?php

namespace TombolaBundle\Repository;

use Doctrine\ORM\EntityRepository;
use UserBundle\Entity\User;

class TombolaParticipantsRepository extends EntityRepository
{
    public function Random_tombolaAction($id_tombola){

        $count = $this->createQueryBuilder('t')
            ->select('COUNT(t)')
            ->where('t.idTombola = :id')
            ->setParameter('id', $id_tombola)
            ->getQuery()
            ->getSingleScalarResult();

        $part = $this->findBy(array('idTombola'=>$id_tombola));
        $count = count($this->findBy(array('idTombola'=>$id_tombola)));
        $rnd = rand(0,$count-1);
        $gagnant = $part[$rnd]->getIdParticipant();
//        echo 'gagant is : '.$gagnant;
        return $gagnant;
    }
    

    public function infoParticipant($id_tombola){

            $query=$this->getEntityManager()
                ->createQuery("select (u.id)as id, (u.nom) as nom, (u.prenom) as prenom, (t.dateInscri) as dateIscription,
            (u.pathPhotoProfil) as photoProfil
      from UserBundle:User u, TombolaBundle:TombolaParticipants t 
            WHERE t.idParticipant IN (SELECT IDENTITY(p.idParticipant) 
                FROM TombolaBundle:TombolaParticipants p
              WHERE p.idTombola = :id)
            
            and t.idParticipant = u.id and t.idTombola = :id");
            $query->setParameter('id',$id_tombola);
        return $query->getResult();

    }


    public function dejaParticiperTombola($id_participant,$id_tombola){
        $count = count($this->findBy(array('idTombola'=>$id_tombola,'idParticipant'=>$id_participant)));
        return  $count;

    }

}