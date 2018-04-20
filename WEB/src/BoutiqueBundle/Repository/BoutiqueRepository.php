<?php

namespace BoutiqueBundle\Repository;
use Doctrine\ORM\EntityRepository;


class BoutiqueRepository extends EntityRepository
{
    public function rechercheAction($word)
    {
        $qb= $this->getEntityManager()->createQueryBuilder();
        $qb->select('b')
            ->from('BoutiqueBundle:Boutique', 'b')
            ->where('b.nom LIKE :word')
            ->setParameter('word', '%'.$word.'%');
        $q = $qb->getQuery();
        return $q->execute();
    }

}