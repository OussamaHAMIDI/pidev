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
        $ob->xAxis->title(array('text' => "Nom de la boutique"));
        $ob->xAxis->categories($namesB);

        $ob->series($boutiqueChart);

        return $this->render('@Statistique/Statistique/afficherStatAdminB.html.twig', array(
            "evaluations" => $topTen, "chart" => $ob
        ));

    }

    public function afficherStatistiqueAdminPAction()
    {
        $em = $this->getDoctrine()->getManager();
        $produitsEvaluees = $em->getRepository('EvaluationBundle:Evaluation')->getBoutiquesEvaluees();
        $i = 0;
        //var_dump($produitsEvaluees);
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
            $namesP[$i] = $produit->getNom();
            $noteP[$i] = $noteMoyenne;
            $i++;
        }


        $produitChart = array(
            array(
                "name" => "Note",
                "data" => $noteP
            ),
        );

        $ob = new Highchart();
        $ob->chart->renderTo('chartProduit');
        $ob->title->text('Top Ten Produit');
        $ob->chart->type('column');
        $ob->yAxis->title(array('text' => "Evaluation"));
        $ob->xAxis->title(array('text' => "Nom du produit"));
        $ob->xAxis->categories($namesP);

        $ob->series($produitChart);

        return $this->render('@Statistique/Statistique/afficherStatAdminB.html.twig', array(
            "evaluations" => $topTen, "chart" => $ob
        ));
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
        $ob->title->text('Nombre d\'utilisateurs par type');
        $ob->chart->height('400');
        $ob->chart->width('600');
        $ob->chart->type('column');
        $ob->yAxis->title(array('text' => "Nombre"));
        $ob->xAxis->title(array('text' => "Type"));
        $ob->xAxis->categories($namesU);
        $ob->series($utilisateursChart);


        $ob2 = new Highchart();
        $ob2->chart->renderTo('chartUtilisateur2');
        $ob2->plotOptions->pie(array(
            'allowPointSelect' => true,
            'cursor' => 'pointer',
            'dataLabels' => array('enabled' => false),
            'showInLegend' => true
        ));
        $ob2->chart->type('pie');
        $ob2->chart->height('400');
        $ob2->chart->width('400');
        $ob2->title->text('Type d\'utilisateurs');


        $data = array(
            array(
                'name' => 'Administrateurs',
                'y' => $nombreAdmin,
                'color' => 'darkGreen',
                'visible' => true
            ),
            array(
                'name' => 'Client',
                'y' => $nombreClient,
                'color' => 'darkOrange',
                'visible' => true
            ), array(
                'name' => 'Artisan',
                'y' => $nombreArtisan,
                'color' => 'blue',
                'visible' => true
            )
        );

        $utilisateursChart2 = array(
            array(
                "name" => "Nombre",
                "data" => $data
            ),
        );

        $ob2->series($utilisateursChart2);

        return $this->render('@Statistique/Statistique/afficherStatAdminU.html.twig', array(
            "chart" => $ob, "chart2" => $ob2
        ));

    }

    public function afficherStatistiqueAdminVAction()
    {

        $nb_vente = array();
        //$nb_vente = [20,80,40,54,35,95,8,21,54,54,21,58];
        for ($i = 1; $i <= 12; $i++) {
            $vente = $this->getDoctrine()->getManager()->getRepository('ReclamationBundle:Reclamation')->vente(
                $i
            );
            array_push($nb_vente, sizeof($vente));
        }

        $mois = array(
            'Jan',
            'Fev',
            'Mar',
            'Avr',
            'Mai',
            'Jun',
            'Jui',
            'Aou',
            'Sep',
            'Oct',
            'Nov',
            'Dec',
        );

        $series = array(
            array("name" => "Nombre de produits vendus", "data" => $nb_vente),
        );
        $ob = new Highchart();
        $ob->chart->renderTo('chartVente');
        $ob->chart->type('bar');
        $ob->chart->height(400);
        $ob->chart->width(800);
        $ob->title->text('Nombre de produits vendus / mois');
        $ob->xAxis->title(array('text' => "Mois"));
        $ob->yAxis->title(array('text' => "Nombre de vente"));
        $ob->xAxis->categories($mois);

        $ob->series($series);

        return $this->render(
            "@Statistique/Statistique/afficherStatAdminV.html.twig", array('chart' => $ob)
        );
    }
}
