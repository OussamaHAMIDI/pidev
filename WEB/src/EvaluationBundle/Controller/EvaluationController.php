<?php

namespace EvaluationBundle\Controller;

use EvaluationBundle\Entity\Evaluation;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

/**
 * Evaluation controller.
 *
 */
class EvaluationController extends Controller
{

    public function evaluationAction($idBoutique)
    {
        $em = $this->getDoctrine()->getManager();
        $evaluations = $em->getRepository("EvaluationBundle:Evaluation")
            ->findBy(array('idBoutique' => $idBoutique));
        $note = 0;
        foreach ($evaluations as $e) {
            $note = $note + $e->getNote();
        }

        $count = $em->getRepository("EvaluationBundle:Evaluation")->count(array('idBoutique' => $idBoutique));
//        var_dump($count);
        if ($count) {
            $noteMoyenne = $note / $count;
            $noteMoyenne = round($noteMoyenne);
        } else {
            $noteMoyenne = 0;
        }
        return $this->render('@Evaluation/Evaluation/evaluation.html.twig', array(
            "noteMoyenne" => $noteMoyenne
        ));
    }

    public function evaluationProduitAction($idProduit)
    {
        $em = $this->getDoctrine()->getManager();
        $evaluations = $em->getRepository("EvaluationBundle:Evaluation")
            ->findBy(array('idProduit' => $idProduit));
        $note = 0;
        foreach ($evaluations as $e) {
            $note = $note + $e->getNote();
        }

        $count = $em->getRepository("EvaluationBundle:Evaluation")->count(array('idProduit' => $idProduit));
//        var_dump($count);
        if ($count) {
            $noteMoyenne = $note / $count;
            $noteMoyenne = round($noteMoyenne);
        } else {
            $noteMoyenne = 0;
        }
        return $this->render('@Evaluation/Evaluation/evaluationProduit.html.twig', array(
            "noteMoyenne" => $noteMoyenne
        ));
    }

    public function afficherEvaluationAction()
    {
        $em = $this->getDoctrine()->getManager();
        $evaluations = $em->getRepository("EvaluationBundle:Evaluation")->findAll();
        return $this->render('@Evaluation/Evaluation/afficher_evaluation.html.twig', array(
            "evaluations" => $evaluations
        ));
    }

    public function afficherTopTenBoutiquesAction()
    {
        $em = $this->getDoctrine()->getManager();
        $boutiquesEvaluees = $em->getRepository('EvaluationBundle:Evaluation')->getBoutiquesEvaluees();
        $i = 0;
        //var_dump($boutiquesEvaluees);
        foreach ($boutiquesEvaluees as $b) {
            if($b != null){
            $evaluation = new Evaluation();
            $evaluations = $em->getRepository("EvaluationBundle:Evaluation")
                ->findBy(array('idBoutique' => $b));
            //var_dump($b);
            $note = 0;
            foreach ($evaluations as $e) {
                $note = $note + $e->getNote();
            }
            $count = $em->getRepository("EvaluationBundle:Evaluation")->count(array('idBoutique' => $b));
            $noteMoyenne = $note / $count;
            $noteMoyenne = round($noteMoyenne);
            $evaluation->setNote($noteMoyenne);
            $boutique = $em->getRepository('BoutiqueBundle:Boutique')->findOneBy(array('id' => $b));
            $boutiques[$i]=$boutique;
            $evaluation->setIdBoutique($boutique);
            $topTen[$i] = $evaluation;
            $i++;
            }
        }
        return $this->render('@Evaluation/Evaluation/topTenBoutiques.html.twig', array(
            "evaluations" => $topTen, "boutiques" => $boutiques
        ));
    }

    public function afficherTopTenProduitsAction()
    {
        $em = $this->getDoctrine()->getManager();
        $produitsEvaluees = $em->getRepository('EvaluationBundle:Evaluation')->getProduitsEvaluees();
        //var_dump($produitsEvaluees);
        $i = 0;
        foreach ($produitsEvaluees as $p) {
            $evaluation = new Evaluation();
            $evaluations = $em->getRepository("EvaluationBundle:Evaluation")
                ->findBy(array('idProduit' => $p));
            //var_dump($p);
            $note = 0;
            foreach ($evaluations as $e) {
                $note = $note + $e->getNote();
            }
            $count = $em->getRepository("EvaluationBundle:Evaluation")->count(array('idProduit' => $p));
            $noteMoyenne = $note / $count;
            $noteMoyenne = round($noteMoyenne);
            $evaluation->setNote($noteMoyenne);
            $produit = $em->getRepository('ProduitBundle:Produit')->findOneBy(array('id' => $p));
            $evaluation->setIdProduit($produit);
            $topTen[$i] = $evaluation;
            $produits[$i] = $produit;
            $i++;
        }
        return $this->render('@Evaluation/Evaluation/topTenProduits.html.twig', array(
            "evaluations" => $topTen , "produits" => $produits
        ));
    }

    public function afficherEvaluerAction($idBoutique)
    {
        return $this->render('@Evaluation/Evaluation/evaluation_details.html.twig', array(
            "idBoutique" => $idBoutique
        ));
    }

    public function afficherEvaluerProduitAction($idProduit)
    {
        return $this->render('@Evaluation/Evaluation/evaluation_details_produit.html.twig', array(
            "idProduit" => $idProduit
        ));
    }

    public function ajouterEvaluationBoutiqueAction($note, $idBoutique)
    {

        $em = $this->getDoctrine()->getManager();
        $ev = $em->getRepository("EvaluationBundle:Evaluation")
            ->findOneBy(array('idBoutique' => $idBoutique,
                'idUser' => $this->container->get('security.token_storage')->getToken()->getUser()));
        if ($ev) {
            $ev->setNote($note);
            $em->persist($ev);
            $em->flush();
        } else {
            $evaluation = new Evaluation();
            $evaluation->setIdUser($this->container->get('security.token_storage')->getToken()->getUser());
            $evaluation->setNote($note);
            $evaluation->setIdBoutique($em->getRepository("BoutiqueBundle:Boutique")
                ->findOneBy(array('id' => $idBoutique)));
            $em->persist($evaluation);
            $em->flush();
        }

        return $this->redirectToRoute("details_boutique", array('id' => $idBoutique));
    }

    public function ajouterEvaluationProduitAction($note, $idProduit)
    {

        $em = $this->getDoctrine()->getManager();
        $ev = $em->getRepository("EvaluationBundle:Evaluation")
            ->findOneBy(array('idProduit' => $idProduit,
                'idUser' => $this->container->get('security.token_storage')->getToken()->getUser()));
        if ($ev) {
            $ev->setNote($note);
            $em->persist($ev);
            $em->flush();
        } else {
            $evaluation = new Evaluation();
            $evaluation->setIdUser($this->container->get('security.token_storage')->getToken()->getUser());
            $evaluation->setNote($note);
            $evaluation->setIdProduit($em->getRepository("ProduitBundle:Produit")
                ->findOneBy(array('id' => $idProduit)));
            $em->persist($evaluation);
            $em->flush();
        }

        return $this->redirectToRoute("afficher_produit_details", array('id' => $idProduit));
    }

//    public function modifierEvaluationBoutiqueAction(Request $request, $note, $idBoutique)
//    {
//        $em = $this->getDoctrine()->getManager();
//        $evaluation = $em->getRepository("EvaluationBundle:Evaluation")
//            ->findOneBy(array('idBoutique' => $idBoutique, 'idUser' => $this->container->get('security.token_storage')->getToken()->getUser()));
//        $form = $this->createForm(EvaluationType::class, $evaluation);
//        $form->handleRequest($request);
//        $evaluation->setNote($note);
//        $em->persist($evaluation);
//        $em->flush();
//        return $this->render('@Evaluation/Evaluation/ajouter_evaluationBoutique.html.twig', array(
//            'form' => $form->createView(),
//            'evaluation' => $evaluation));
//    }

}
