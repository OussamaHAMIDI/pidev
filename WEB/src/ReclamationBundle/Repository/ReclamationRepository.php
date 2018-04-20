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

    public function vente($mois){
        $result = $this->getEntityManager()->createQuery('SELECT p FROM PanierBundle:ProduitPanier p WHERE 
        MONTH(p.dateAjout) = :m')
            ->setParameter('m',$mois)
            ->getResult();
        return  $result;
    }

    public function venteArtisan($mois,$id_artisan){
        $result = $this->getEntityManager()->createQuery(
            'SELECT p FROM PanierBundle:ProduitPanier p , ProduitBundle:Produit pr , BoutiqueBundle:Boutique b  WHERE 
        p.idProduit = : pr.id and b.idUser = :id_artisan and MONTH(p.dateAjout) = :m ')
            ->setParameter('m',$mois) ->setParameter('id_artisan',$id_artisan)
            ->getResult();
        return  $result;
    }


    public function peutEvaluer($idProduit){
        $result = $this->getEntityManager()->createQuery(
            'SELECT p FROM PanierBundle:ProduitPanier pp , PanierBundle:Panier p 
                  WHERE 
                  p.idUser = :id_user and p.id = : pp.idPanier and pp.idProduit = : id_produit')
            ->setParameter('id_user',$this->container->get('security.token_storage')->getToken()->getUser())
            ->setParameter('id',$idProduit)
            ->getResult();
        return  $result;
    }


}