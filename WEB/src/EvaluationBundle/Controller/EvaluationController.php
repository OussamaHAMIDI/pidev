<?php

namespace EvaluationBundle\Controller;

use EvaluationBundle\Entity\Evaluation;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

/**
 * Evaluation controller.
 *
 */
class EvaluationController extends Controller
{

    public function afficherEvaluationAction()
    {
        $em= $this->getDoctrine()->getManager();
        $evaluations=$em->getRepository("EvaluationBundle:Evaluation")->findAll();
        return $this->render('@Evaluation/Evaluation/afficher_evaluation.html.twig', array(
            "evaluations"=>$evaluations
        ));
    }


}
