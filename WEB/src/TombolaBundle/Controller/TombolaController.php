<?php

namespace TombolaBundle\Controller;

use Ob\HighchartsBundle\Highcharts\Highchart;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
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

//                echo "amboula tzedit";
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

        // Chart
        $series = array(
            array("name" => "Nb de tombolas", "data" => $nb_tombola),
        );
        $ob = new Highchart();
        $ob->chart->renderTo('linechart'); //  #id du div où afficher le graphe
        $ob->title->text('Mes tombolas ajoutées en fonction des mois');
        $ob->xAxis->title(array('text' => "Mois"));
        $ob->yAxis->title(array('text' => "Nb de tombola"));
        $ob->xAxis->categories($mois);
        $ob->series($series);

        return $this->render(
            "@Tombola/back/afficherTombolasArtisan.html.twig",
            array('tombolas' => $tombolas, 'chart' => $ob)
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


        return $this->redirectToRoute("detailsFront", array('id' => $part->getIdTombola()->getId()));
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

//        $participants = $this->getDoctrine()->getManager()->getRepository(
//            'TombolaBundle:TombolaParticipants')->findBy(array('idTombola'=> $id));


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
        $tombolas = $em->getRepository('TombolaBundle:Tombola')->recherchebytitreFRONTAction(
            $searchParameter,
            $this->getUser()->getId()
        );

        return $this->render("@Tombola/back/rechercheAll.html.twig", array('tombolas' => $tombolas));

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

        return $this->redirectToRoute("detailsFront", array('id' => $tombola->getId()));

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
//        $this->get('mailer')->send($contenu_mail);

        $em->flush();

        return $this->redirectToRoute("details", array('id' => $tombola->getId()));
    }

}


