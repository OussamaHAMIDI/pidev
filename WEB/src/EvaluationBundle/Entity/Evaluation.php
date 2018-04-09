<?php

namespace EvaluationBundle\Entity;


use Doctrine\ORM\Mapping as ORM;
use UserBundle\Entity\User as User;
use BoutiqueBundle\Entity\Boutique as Boutique;
use ProduitBundle\Entity\Produit as Produit;

/**
 * Evaluation
 *
 * @ORM\Table(name="evaluation", indexes={@ORM\Index(name="id_boutique", columns={"id_boutique"}), @ORM\Index(name="id_user", columns={"id_user"}), @ORM\Index(name="id_produit", columns={"id_produit"})})
 * @ORM\Entity
 */

class Evaluation
{

    public function __construct()
    {
        $this->dateCreation = new \DateTime();
    }

    /**
     * @var integer
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var integer
     *
     * @ORM\Column(name="note", type="integer", nullable=false)
     */
    private $note;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_creation", type="datetime", nullable=true)
     */
    private $dateCreation;

    /**
     * @var Boutique
     *
     * @ORM\ManyToOne(targetEntity="BoutiqueBundle\Entity\Boutique")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_boutique", referencedColumnName="id")
     * })
     */
    private $idBoutique;

    /**
     * @var Produit
     *
     * @ORM\ManyToOne(targetEntity="ProduitBundle\Entity\Produit")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_produit", referencedColumnName="id")
     * })
     */
    private $idProduit;

    /**
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id")
     * })
     */
    private $idUser;



    /**
     * Get id
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set note
     *
     * @param integer $note
     *
     * @return Evaluation
     */
    public function setNote($note)
    {
        $this->note = $note;

        return $this;
    }

    /**
     * Get note
     *
     * @return integer
     */
    public function getNote()
    {
        return $this->note;
    }

    /**
     * Set dateCreation
     *
     * @param \DateTime $dateCreation
     *
     * @return Evaluation
     */
    public function setDateCreation($dateCreation)
    {
        $this->dateCreation = $dateCreation;

        return $this;
    }

    /**
     * Get dateCreation
     *
     * @return \DateTime
     */
    public function getDateCreation()
    {
        return $this->dateCreation;
    }

    /**
     * Set idBoutique
     *
     * @param Boutique $idBoutique
     *
     * @return Evaluation
     */
    public function setIdBoutique(Boutique $idBoutique = null)
    {
        $this->idBoutique = $idBoutique;

        return $this;
    }

    /**
     * Get idBoutique
     *
     * @return Boutique
     */
    public function getIdBoutique()
    {
        return $this->idBoutique;
    }

    /**
     * Set idProduit
     *
     * @param Produit $idProduit
     *
     * @return Evaluation
     */
    public function setIdProduit(Produit $idProduit = null)
    {
        $this->idProduit = $idProduit;

        return $this;
    }

    /**
     * Get idProduit
     *
     * @return Produit
     */
    public function getIdProduit()
    {
        return $this->idProduit;
    }

    /**
     * Set idUser
     *
     * @param User $idUser
     *
     * @return Evaluation
     */
    public function setIdUser(User $idUser = null)
    {
        $this->idUser = $idUser;

        return $this;
    }

    /**
     * Get idUser
     *
     * @return User
     */
    public function getIdUser()
    {
        return $this->idUser;
    }
}
