<?php

namespace TombolaBundle\Controller;

use Ob\HighchartsBundle\Highcharts\Highchart;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Response;
use TombolaBundle\Entity\TombolaParticipants;
use TombolaBundle\Form\TombolaEditType;
use TombolaBundle\Form\TombolaType;
use TombolaBundle\Entity\Tombola;
use Symfony\Component\HttpFoundation\Request;

class TombolaController extends Controller
{
    /**
     * @Route("/ajouter", name="ajouter_tombola")
     */
    public function ajouterTombolaAction(Request $request)
    {
        $tombola = new Tombola();
        $form = $this->createForm(TombolaType::class, $tombola);
        $tombola->setIdArtisan($this->getUser());
        //$form->handleRequest($request);
        if ($request->isMethod('post')) {
            $form->handleRequest($request);
            if ($form->isValid()) {
                $tombola = $form->getData();
                $em = $this->getDoctrine()->getManager();
                $em->persist($tombola);
                $em->flush();

                $this->container->get('session')->getFlashBag()->add('success',
                    'Tombola '.$tombola->getTitre().' est ajoutée avec succès');

                return $this->redirectToRoute("afficherTombolasArtisan");
            }
        }

        return $this->render(
            "@Tombola/back/ajouterTombola.html.twig",
            array("form" => $form->createView(), "path" => "")
        );
    }

    /**
     * @Route("/afficherTombolas", name="afficherTombolasArtisan")
     */
    public function afficherTombolasArtisanAction()
    {

        $tombolas = $this->getDoctrine()->getManager()
            ->getRepository('TombolaBundle:Tombola')->findBy(array('idArtisan' => $this->getUser()));

        $nb_tombola = array();
        for ($i = 1; $i <= 12; $i++) {
            $mois = $this->getDoctrine()->getManager()->getRepository('TombolaBundle:Tombola')->moisTombolaAction(
                $i,
                $this->getUser()->getId()
            );
            array_push($nb_tombola, sizeof($mois));
        }
        $c = count($this->getDoctrine()->getManager()->getRepository('TombolaBundle:Tombola')->tombolasCloture($this->getUser()));
        $f = count($this->getDoctrine()->getManager()->getRepository('TombolaBundle:Tombola')->tombolasFerme($this->getUser()));
        $o = count($this->getDoctrine()->getManager()->getRepository('TombolaBundle:Tombola')->tombolasOuverte($this->getUser()));


        $mois = array(
            'janvier',
            'fevrier',
            'mars',
            'avril',
            'mai',
            'juin',
            'juillet',
            'aout',
            'septembre',
            'octobre',
            'novembre',
            'decembre',
        );

        // Chart 1
        $series = array(
            array("name" => "Nombre de tombolas", "data" => $nb_tombola),
        );
        $ob = new Highchart();
        $ob->chart->type('column');
        $ob->chart->renderTo('linechart'); //  #id du div où afficher le graphe
        $ob->title->text('Mes tombolas ajoutées en fonction des mois');
        $ob->xAxis->title(array('text' => "Mois"));
        $ob->yAxis->title(array('text' => "Nb de tombola"));
        $ob->xAxis->categories($mois);
        $ob->series($series);


       
        $ob2 = new Highchart();
        $ob2->chart->renderTo('linechart2');
        $ob2->title->text('Mes tombolas ajoutées en fonction des etats');
        $ob2->plotOptions->pie(array(
            'allowPointSelect'  => true,
            'cursor'    => 'pointer',
            'dataLabels'    => array('enabled' => false),
            'showInLegend'  => true
        ));
        $data = array(
            array(
                'name' => 'Cloturée',
                'y' => $c,
                'color' => 'red',
                'visible' => true
            ),
            array(
                'name' => 'Fermée',
                'y' => $f,
                'color' => 'orange',
                'visible' => true
            ),  array(
                'name' => 'Ouverte',
                'y' => $o,
                'color' => 'green',
                'visible' => true
            )
     );
        $ob2->series(array(array('type' => 'pie','name' => 'Nombre', 'data' => $data)));



//        $ob3 = new Highchart();
//        $ob3->chart->renderTo('container');
//        $ob3->chart->type('pie');
//        $ob3->title->text('Browser market shares. November, 2013.');
//        $ob3->plotOptions->series(
//            array(
//                'dataLabels' => array(
//                    'enabled' => true,
//                    'format' => '{point.name}: {point.y}'
//                )
//            )
//        );
//
//        $ob3->tooltip->headerFormat('<span style="font-size:11px">{series.name}</span><br>');
//        $ob3->tooltip->pointFormat('<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>');
//
//        $data = array(
//            array(
//                'name' => 'Cloturée',
//                'y' => $c,
//                'drilldown' => 'Cloturé',
//                'color' => 'red',
//                'visible' => true
//            ),
//            array(
//                'name' => 'Fermée',
//                'y' => $f,
//                'drilldown' => 'Fermée',
//                'color' => 'orange',
//                'visible' => true
//            ),  array(
//                'name' => 'Ouverte',
//                'y' => $o,
//                'drilldown' => 'Ouverte',
//                'color' => 'green',
//                'visible' => true
//            )
//        );
//        $ob3->series(
//            array(
//                array(
//                    'name' => 'Nombre',
//                    'colorByPoint' => true,
//                    'data' => $data
//                )
//            )
//        );
        
        

        return $this->render(
            "@Tombola/back/afficherTombolasArtisan.html.twig",
            array('tombolas' => $tombolas, 'chart' => $ob,'chart2'=>$ob2)
        );
    }

    /**
     * @Route("/afficherTombolasFront", name="afficherTombolas")
     */
    public function afficherTombolasAction()
    {
        $tombolas = $this->getDoctrine()->getManager()
            ->getRepository('TombolaBundle:Tombola')->findAll();

        return $this->render("@Tombola/front/afficherTombolas.html.twig", array('tombolas' => $tombolas));
    }

    /**
     * @Route("/supprimer/{id}", name="supprimer")
     */
    public function supprimerTombolaAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $tombola = $em->getRepository("TombolaBundle:Tombola")->findOneBy(array("id" => $id));
        $em->remove($tombola);
        $em->flush();

        $this->container->get('session')->getFlashBag()->add('sup',
            'Tombola '.$tombola->getTitre().' est supprimée avec succées');

        return $this->redirectToRoute("afficherTombolasArtisan");
    }

    /**
     * @Route("/supprimerPart/{id}", name="supprimerPart")
     */
    public function supprimerPartTombolaAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $part = $em->getRepository("TombolaBundle:TombolaParticipants")->findOneBy(
            array("idParticipant" => $this->getUser()->getId(),"idTombola" => $id)
        );
        $em->remove($part);
        $em->flush();

        $this->container->get('session')->getFlashBag()->add('sup',
            $this->getUser()->getPrenom().' votre participation est annulée, vous n\'avez plus de chance pour gagner la tombola '
        .$part->getIdTombola()->getTitre().'!');

        return $this->redirectToRoute("afficherTombolas" );//, array('id' => $part->getIdTombola()->getId()));
    }

    /**
     * @Route("/modifier/{id}", name="modifier")
     */
    public function modifierTombolaAction(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $tombola = $em->getRepository('TombolaBundle:Tombola')->findOneBy(array("id" => $id));

        $oldPath = $tombola->getPath();

        $path = $tombola->getUploadRootDir().'\\'.$tombola->getPath();
        $tombola->setPath($path);

        $form = $this->createForm(TombolaEditType::class, $tombola);

        $form->handleRequest($request);
        if ($form->isValid()) {

            $file = $form['file']->getData();
            if ($file == null) {
//                echo'file not exist';
                $tombola->setPath($oldPath);
            }

            $tombola->setDateModif(new \DateTime());
            $em->persist($tombola);
            $em->flush();

            $this->container->get('session')->getFlashBag()->add('success',
                'Tombola '.$tombola->getTitre().' est modifieé avec succès');

            return $this->redirectToRoute("afficherTombolasArtisan");
        }

        return $this->render(
            "@Tombola/back/modifierTombola.html.twig",
            array("form" => $form->createView(), "path" => $oldPath)
        );
    }

    /**
     * @Route("/detail/{id}", name="details")
     */
    public function detailTombolaAction($id)
    {
        if ($id === null) {
            return $this->redirectToRoute("afficherTombolasArtisan");
        }

        $tombola = $this->getDoctrine()->getManager()
            ->getRepository('TombolaBundle:Tombola')->findOneBy(array('id' => $id));

        $participants = $this->getDoctrine()->getManager()->getRepository(
            'TombolaBundle:TombolaParticipants'
        )->infoParticipant($id);

//       return $this->render("@Tombola/Notif/notification.html.twig");
//
        $this->notifAction();
        return $this->render(
            "@Tombola/back/detailsTombola.html.twig",
            array('tombola' => $tombola, 'participants' => $participants)
        );
    }



    /**
     * @Route("/rechercheAJAX", name="rechercheAJAX_tombola")
     */
    public function searchAJAXAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $searchParameter = $request->get('key');
        if(strlen($searchParameter) >0){
            $tombolas = $em->getRepository('TombolaBundle:Tombola')->recherchebytitreFRONTAction(
                $searchParameter,
                $this->getUser()->getId()
            );

            return $this->render("@Tombola/back/rechercheAll.html.twig", array('tombolas' => $tombolas));

        }
       return false;
    }

    /**
     * @Route("/rechercheAJAX2", name="rechercheAJAXFront_tombola")
     */
    public function searchAJAXFrontAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $searchParameter = $request->get('key');
        $tombolas = $em->getRepository('TombolaBundle:Tombola')->rechercheAction($searchParameter);

        return $this->render("@Tombola/front/rechercheAll.html.twig", array('tombolas' => $tombolas));

    }

    /**
     * @Route("/detailFront/{id}", name="detailsFront")
     */
    public function detailsFrontTombolaAction($id)
    {
        if ($id == null) {
            return $this->redirectToRoute("afficherTombolas");
        }
        $participe = false;
        if ($this->getUser() != null) {
            // verif si user connected a deja participé
            $deja = $this->getDoctrine()->getManager()->getRepository('TombolaBundle:TombolaParticipants')->
            dejaParticiperTombola($this->getUser()->getId(),$id);

            if ($deja > 0) {
                $participe = true;
            } else {
                $participe = false;
            }
        }

        $tombola = $this->getDoctrine()->getManager()
            ->getRepository('TombolaBundle:Tombola')->findOneBy(array('id' => $id));

        $participants = $this->getDoctrine()->getManager()->getRepository(
            'TombolaBundle:TombolaParticipants'
        )->infoParticipant($id);

        return $this->render(
            "@Tombola/front/detailsTombola.html.twig",
            array(
                'tombola' => $tombola,
                'participants' => $participants,
                'participe' => $participe,
            )
        );
    }

    /**
     * @Route("/participer/{id}", name="participer")
     */
    public function participerTombolaAction($id)
    {
        $part = new TombolaParticipants();
        $em = $this->getDoctrine()->getManager();
        $tombola = $em->getRepository('TombolaBundle:Tombola')->findOneBy(array("id" => $id));


        $part->setIdParticipant($this->getUser());
        $part->setDateInscri(new \DateTime());
        $part->setIdTombola($tombola);

        $em->persist($part);
        $em->flush();

        $this->container->get('session')->getFlashBag()->add('success',
            $this->getUser()->getPrenom().' vous êtes parmis les participants du tombola '.$tombola->getTitre().'.');

        return $this->redirectToRoute("afficherTombolas" );//, array('id' => $part->getIdTombola()->getId()));

    }


    /**
     * @Route("/lancerTirage/{id}", name="lancerTirage")
     */
    public function lancerTirageAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $tombola = $em->getRepository('TombolaBundle:Tombola')->findOneBy(array("id" => $id));

        $gagnant = $em->getRepository('TombolaBundle:TombolaParticipants')->Random_tombolaAction($tombola->getId());
//        var_dump($randomPart);
        $tombola->setIdGagnant($gagnant);

        //envoyer un mail au gagnant
        $contenu_mail = \Swift_Message::newInstance()
            ->setSubject('Félicitation')
            ->setFrom(array('souklemdina@gmail.com' => 'Souk lemdina Team'))
            ->setTo($gagnant->getEmail())
            ->setCharset('utf-8')
            ->setContentType('text/html')
            ->setBody($this->renderView('@Tombola/SwiftView/gagnant.html.twig', array('tombola' => $tombola)));
        $this->get('mailer')->send($contenu_mail);

        $em->flush();

        $this->container->get('session')->getFlashBag()->add('success',
            'Désormais cette tombola est cloturée, un email a été enovoyé au gagnant '.$gagnant->getPrenom().' '.$gagnant->getNom());


        return $this->redirectToRoute("details", array('id' => $tombola->getId()));
    }

//    /**
//     * @Route("{id}/supprimer/",name="supprimerPartAjax")
//     * @Method({"GET", "POST"})
//     */
//    public function supprimerProduitPanier(Request $request,$id)
//    {
//        if ($request->isXMLHttpRequest()) {
//            $post_data = $request->request->all();
//
//            $data = json_decode($request->getContent());
//            var_dump(json_decode($id));
//
//
////            echo $id;
////            echo json_decode($id);
////            $tombolas = $this->get('session')->get('tombolas') ;
////            foreach ($tombolas as $key => $object) {
////
////                if ($object->idProduit == $id) {
////                    $prod = $tombolas[$key];
////                    $monpanier = $this->get('session')->get('monpanier');
////                    $monpanier = $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())-(floatval($prod->getPrixVente())*floatval($prod->getQuantiteVendu())));
////                    $this->get('session')->set('monpanier',$monpanier);
////                    unset($tombolas[$key]);
////                }
////            }
////            $reindex = array_values($tombolas);
////            $tombolas = $reindex;
////            $this->get('session')->set('tombolas',$tombolas);
//
//            return new Response(json_encode($data));
//        }
//
//        return new Response('This is not ajax!', 400);
//
//
//    }

}


