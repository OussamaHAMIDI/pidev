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