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
        $qb = $this->getEntityManager()->createQueryBuilder();
        $qb->select('p')
            ->from('TombolaBundle:Tombola', 'p')
            ->where('p.titre LIKE :word')
            ->setParameter('word', '%'.$word.'%');

        $q = $qb->getQuery();

        return $q->execute();
    }


    public function recherchebytitreFRONTAction($word, $id)
    {
        $qb = $this->getEntityManager()->createQueryBuilder();
        $qb->select('p')
            ->from('TombolaBundle:Tombola', 'p')
            ->where('p.titre LIKE :word')->andWhere('p.idArtisan = :id')
            ->setParameter('word', '%'.$word.'%')
            ->setParameter('id', $id);
        $q = $qb->getQuery();

        return $q->execute();
    }

    public function recherchebytitreAction($word)
    {
        $qb = $this->getEntityManager()->createQueryBuilder();
        $qb->select('p')
            ->from('TombolaBundle:Tombola', 'p')
            ->where('p.titre LIKE :word')
            ->setParameter('word', '%'.$word.'%');
        $q = $qb->getQuery();

        return $q->execute();
    }

    public function moisTombolaAction($mois, $id_artisan)
    {
        $qb = $this->createQueryBuilder('t')
            ->select('t')
            ->where('MONTH(t.dateAjout) = :m')
            ->andWhere('t.idArtisan = :id')
            ->setParameter('m', $mois)->setParameter('id', $id_artisan)
            ->getQuery()
            ->getResult();

        return $qb;
    }

    public function tombolasCloture($id_artisan)
    {
        $qb = $this->createQueryBuilder('t')
            ->select('t')
            ->where('t.dateTirage < :now')
            ->andWhere('t.idGagnant IS NOT NULL')
            ->andWhere('t.idArtisan = :id')
            ->setParameter('now', new \DateTime())
            ->setParameter('id', $id_artisan)
            ->getQuery()
            ->getResult();

        return $qb;
    }

    public function tombolasFerme($id_artisan)
    {
        $qb = $this->createQueryBuilder('t')
            ->select('t')
            ->where('t.dateTirage < :now')
            ->andWhere('t.idGagnant IS NULL')
            ->andWhere('t.idArtisan = :id')
            ->setParameter('now', new \DateTime())
            ->setParameter('id', $id_artisan)
            ->getQuery()
            ->getResult();

        return $qb;
    }

    public function tombolasOuverte($id_artisan)
    {
        $qb = $this->createQueryBuilder('t')
            ->select('t')
            ->where('t.dateTirage > :now')
            ->andWhere('t.idGagnant IS NULL')
            ->andWhere('t.idArtisan = :id')
            ->setParameter('now', new \DateTime())
            ->setParameter('id', $id_artisan)
            ->getQuery()
            ->getResult();

        return $qb;
    }

}