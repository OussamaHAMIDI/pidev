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
//        $evaluations = $em->getRepository("EvaluationBundle:Evaluation")
//            ->findBy(array(), array('note' => 'desc'));
        $boutiquesEvaluees = $em->getRepository('EvaluationBundle:Evaluation')->getBoutiquesEvaluees();
        //var_dump($boutiquesEvaluees);
        //var_dump($evaluations);
        $i = 0;
        foreach ($boutiquesEvaluees as $b) {
            $evaluation = new Evaluation();
            $evaluations = $em->getRepository("EvaluationBundle:Evaluation")
                ->findBy(array('idBoutique' => $b));
            var_dump($b);
            $note = 0;
            foreach ($evaluations as $e) {
                $note = $note + $e->getNote();
            }
            $count = $em->getRepository("EvaluationBundle:Evaluation")->count(array('idBoutique' => $b));
            $noteMoyenne = $note / $count;
            $noteMoyenne = round($noteMoyenne);
            $evaluation->setNote($noteMoyenne);
            $boutique = $em->getRepository('BoutiqueBundle:Boutique')->findOneBy(array('id' => $b));
            $evaluation->setIdBoutique($boutique);
            $topTen[$i] = $evaluation;
            $i++;
        }
        return $this->render('@Evaluation/Evaluation/topTenBoutiques.html.twig', array(
            "evaluations" => $topTen
        ));
    }

    public function afficherEvaluerAction($idBoutique){
        return $this->render('@Evaluation/Evaluation/evaluation_details.html.twig', array(
            "idBoutique" => $idBoutique
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
