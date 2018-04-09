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
        $noteMoyenne = 0 ;
        foreach ($evaluations as $e) {
            $noteMoyenne = $noteMoyenne + $e->getNote();
        }

        $count = $this->createQueryBuilder('e')
            ->select('COUNT(e)')
            ->where('e.id = :id')
            ->setParameter('idBoutique', $idBoutique)
            ->getQuery()
            ->getSingleScalarResult();

        return $this->render('@Evaluation/Evaluation/evaluation.html.twig', array(
            "noteMoyenne" => $noteMoyenne/$count
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

    public function ajouterEvaluationBoutiqueAction($note, $idBoutique)
    {

        $em = $this->getDoctrine()->getManager();
        $ev = $em->getRepository("EvaluationBundle:Evaluation")
            ->findOneBy(array('idBoutique' => $idBoutique,
                'idUser' => $this->container->get('security.token_storage')->getToken()->getUser()));
        if($ev){
            $ev->setNote($note);
            $em->persist($ev);
            $em->flush();
        }else{
            $evaluation = new Evaluation();
            $evaluation->setIdUser($this->container->get('security.token_storage')->getToken()->getUser());
            $evaluation->setNote($note);
            $evaluation->setIdBoutique($em->getRepository("BoutiqueBundle:Boutique")
                ->findOneBy(array('id' => $idBoutique)));
            $em->persist($evaluation);
            $em->flush();
        }

        return $this->render('@Evaluation/Evaluation/ajouter_evaluationBoutique.html.twig');
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
