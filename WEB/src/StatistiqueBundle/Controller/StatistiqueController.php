<?php

namespace StatistiqueBundle\Controller;

use Ob\HighchartsBundle\Highcharts\Highchart;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use EvaluationBundle\Entity\Evaluation;

class StatistiqueController extends Controller
{

    public function afficherStatistiqueAdminBAction()
    {
        $em = $this->getDoctrine()->getManager();
        $boutiquesEvaluees = $em->getRepository('EvaluationBundle:Evaluation')->getBoutiquesEvaluees();
        $i = 0;
        //var_dump($boutiquesEvaluees);
        foreach ($boutiquesEvaluees as $b) {
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
            $evaluation->setIdBoutique($boutique);
            $topTen[$i] = $evaluation;
            $namesB[$i] = $boutique->getNom();
            $noteB[$i] = $noteMoyenne;
            $i++;
        }


        $boutiqueChart = array(
            array(
                "name" => "Note",
                "data" => $noteB
            ),
        );
        $ob = new Highchart();
        $ob->chart->renderTo('chartBoutique');
        $ob->title->text('Top Ten Boutiques');
        $ob->chart->type('column');
        $ob->yAxis->title(array('text' => "Evaluation"));
        $ob->xAxis->title(array('text' => "Nom du boutique"));
        $ob->xAxis->categories($namesB);

        $ob->series($boutiqueChart);

        return $this->render('@Statistique/Statistique/afficherStatAdminB.html.twig', array(
            "evaluations" => $topTen, "chart" => $ob
        ));

    }

    public function afficherStatistiqueAdminPAction(){

    }
    public function afficherStatistiqueAdminUAction()
    {
        $em = $this->getDoctrine()->getManager();
        $nombreAdmin = $em->getRepository("UserBundle:User")->count(array('type' => 'Administrateur'));
        $nombreArtisan = $em->getRepository("UserBundle:User")->count(array('type' => 'Artisan'));
        $nombreClient = $em->getRepository("UserBundle:User")->count(array('type' => 'Client'));


        $namesU = array(
            'Administrateur',
            'Artisan',
            'Client',
        );
        $nombreU = array(
            $nombreAdmin,
            $nombreArtisan,
            $nombreClient
        );

        $utilisateursChart = array(
            array(
                "name" => "Nombre",
                "data" => $nombreU
            ),
        );
        $ob = new Highchart();
        $ob->chart->renderTo('chartUtilisateur');
        $ob->title->text('Nombre d\'utilisateurs par Type');
        $ob->chart->type('column');
        $ob->yAxis->title(array('text' => "Nombre"));
        $ob->xAxis->title(array('text' => "Type"));
        $ob->xAxis->categories($namesU);

        $ob->series($utilisateursChart);

        return $this->render('@Statistique/Statistique/afficherStatAdminU.html.twig', array(
            "chart" => $ob
        ));

    }

//    public function afficherStatistiqueArtisanAction()
//    {
//
////        $tombolas = $this->getDoctrine()->getManager()
////            ->getRepository('TombolaBundle:Tombola')->findBy(array('idArtisan' => $this->getUser()));
////
////        $nb_tombola = array();
////        for ($i = 1; $i <= 12; $i++) {
////            $mois = $this->getDoctrine()->getManager()->getRepository('TombolaBundle:Tombola')->moisTombolaAction(
////                $i,
////                $this->getUser()->getId()
////            );
////            array_push($nb_tombola, sizeof($mois));
////        }
////
////        $mois = array(
////            'janvier',
////            'fevrier',
////            'mars',
////            'avril',
////            'mai',
////            'juin',
////            'juillet',
////            'aout',
////            'septembre',
////            'octobre',
////            'novembre',
////            'decembre',
////        );
////
////        // Chart
////        $series = array(
////            array("name" => "Nb de tombolas", "data" => $nb_tombola),
////        );
////        $ob = new Highchart();
////        $ob->chart->renderTo('linechart'); //  #id du div où afficher le graphe
////        $ob->title->text('Mes tombolas ajoutées en fonction des mois');
////        $ob->xAxis->title(array('text' => "Mois"));
////        $ob->yAxis->title(array('text' => "Nb de tombola"));
////        $ob->xAxis->categories($mois);
////        $ob->series($series);
//
//        return $this->render(
//            "@Statistique/Statistique/afficherStatAdminB.html.twig"
//            //,
//            //array('tombolas' => $tombolas, 'chart' => $ob)
//        );
//    }
}
