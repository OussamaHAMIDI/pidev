<?php

namespace SoukBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use EvaluationBundle\Entity\Evaluation;

class EvaluationController extends Controller
{
    
    /**
 * @Route("/api/evaluation/all")
 */
    public function allAction()
    {
        $evaluations = $this->getDoctrine()->getManager()
            ->getRepository('EvaluationBundle:Evaluation')
            ->findAll();
        foreach ($evaluations as $evaluation) {
            $evaluation->setDateCreation($evaluation->getDateCreation()->format('Y-m-d H:i:s'));
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($evaluations);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/evaluation/topBoutique")
     */
    public function topBoutiqueAction()
    {
        $em = $this->getDoctrine()->getManager();
        $boutiquesEvaluees = $em->getRepository('EvaluationBundle:Evaluation')->getBoutiquesEvaluees();
        $i = 0;
        foreach ($boutiquesEvaluees as $b) {
            $evaluation = new Evaluation();
            $evaluations = $em->getRepository("EvaluationBundle:Evaluation")
                ->findBy(array('idBoutique' => $b));
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
        foreach ($topTen as $evaluation) {
            $evaluation->setDateCreation($evaluation->getDateCreation()->format('Y-m-d H:i:s'));
        }

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($topTen);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/evaluation/vente/{id}")
     */
    public function venteAction($id)
    {

        $vente = $this->getDoctrine()->getManager()->getRepository('ReclamationBundle:Reclamation')
            ->venteArtisanMobile($id);
        $total = $this->getDoctrine()->getManager()->getRepository('ReclamationBundle:Reclamation')
            ->totalArtisanMobile($id);
        $resp = array();
        $resp[0]=$vente;
        $resp[1]=$total;
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($resp);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/evaluation/peut/{id}")
     */
    public function peutAction($id)
    {

        $peut = $this->getDoctrine()->getManager()->getRepository('ReclamationBundle:Reclamation')
            ->pe($id);
        $total = $this->getDoctrine()->getManager()->getRepository('ReclamationBundle:Reclamation')
            ->totalArtisanMobile($id);
        $resp = array();
        $resp[0]=$vente;
        $resp[1]=$total;
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($resp);
        return new JsonResponse($formatted);
    }


    /**
     * @Route("/api/evaluation/find/{id}")
     */
    public function findAction($id)
    {
        $evaluation = $this->getDoctrine()->getManager()
            ->getRepository('EvaluationBundle:Evaluation')
            ->find($id);
        $evaluation->setDateCreation($evaluation->getDateCreation()->format('Y-m-d H:i:s'));
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($evaluation);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/evaluation/addBoutique")
     */
    public function ajouterBoutiqueAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $evaluation = $em->getRepository("EvaluationBundle:Evaluation")
            ->findOneBy(array('idBoutique' => $request->get('idBoutique'),
                'idUser' => $request->get('idUser')));
        if ($evaluation) {
            $evaluation->setNote($request->get('note'));
            $em->persist($evaluation);
            $em->flush();
        } else {
            $evaluation = new Evaluation();
            $boutique = $em->getRepository('BoutiqueBundle:Boutique')->find($request->get('idBoutique'));
            $evaluation->setIdBoutique($boutique);
            $user = $em->getRepository('UserBundle:User')->find($request->get('idUser'));
            $evaluation->setIdUser($user);

            $evaluation->setNote($request->get('note'));
            $em->persist($evaluation);
            $em->flush();
            $evaluation->setDateCreation($evaluation->getDateCreation()->format('Y-m-d H:i:s'));
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($evaluation);
        return new JsonResponse($formatted);
    }

}
