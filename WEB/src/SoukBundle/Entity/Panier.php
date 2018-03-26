<?php

namespace SoukBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Panier
 *
 * @ORM\Table(name="panier", indexes={@ORM\Index(name="id_user", columns={"id_user"})})
 * @ORM\Entity
 */
class Panier
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_creation", type="datetime", nullable=false)
     */
    private $dateCreation;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_livraison", type="datetime", nullable=true)
     */
    private $dateLivraison;

    /**
     * @var string
     *
     * @ORM\Column(name="total_ttc", type="decimal", precision=7, scale=3, nullable=false)
     */
    private $totalTtc;

    /**
     * @var string
     *
     * @ORM\Column(name="frais_livraison", type="decimal", precision=7, scale=0, nullable=false)
     */
    private $fraisLivraison;

    /**
     * @var string
     *
     * @ORM\Column(name="statut", type="string", length=20, nullable=false)
     */
    private $statut;

    /**
     * @var string
     *
     * @ORM\Column(name="mode_paiement", type="string", length=20, nullable=false)
     */
    private $modePaiement;

    /**
     * @var string
     *
     * @ORM\Column(name="mode_livraison", type="string", length=20, nullable=true)
     */
    private $modeLivraison;

    /**
     * @var boolean
     *
     * @ORM\Column(name="est_livre", type="boolean", nullable=false)
     */
    private $estLivre;

    /**
     * @var boolean
     *
     * @ORM\Column(name="est_paye", type="boolean", nullable=false)
     */
    private $estPaye;

    /**
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="User")
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
     * Set dateCreation
     *
     * @param \DateTime $dateCreation
     *
     * @return Panier
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
     * Set dateLivraison
     *
     * @param \DateTime $dateLivraison
     *
     * @return Panier
     */
    public function setDateLivraison($dateLivraison)
    {
        $this->dateLivraison = $dateLivraison;

        return $this;
    }

    /**
     * Get dateLivraison
     *
     * @return \DateTime
     */
    public function getDateLivraison()
    {
        return $this->dateLivraison;
    }

    /**
     * Set totalTtc
     *
     * @param string $totalTtc
     *
     * @return Panier
     */
    public function setTotalTtc($totalTtc)
    {
        $this->totalTtc = $totalTtc;

        return $this;
    }

    /**
     * Get totalTtc
     *
     * @return string
     */
    public function getTotalTtc()
    {
        return $this->totalTtc;
    }

    /**
     * Set fraisLivraison
     *
     * @param string $fraisLivraison
     *
     * @return Panier
     */
    public function setFraisLivraison($fraisLivraison)
    {
        $this->fraisLivraison = $fraisLivraison;

        return $this;
    }

    /**
     * Get fraisLivraison
     *
     * @return string
     */
    public function getFraisLivraison()
    {
        return $this->fraisLivraison;
    }

    /**
     * Set statut
     *
     * @param string $statut
     *
     * @return Panier
     */
    public function setStatut($statut)
    {
        $this->statut = $statut;

        return $this;
    }

    /**
     * Get statut
     *
     * @return string
     */
    public function getStatut()
    {
        return $this->statut;
    }

    /**
     * Set modePaiement
     *
     * @param string $modePaiement
     *
     * @return Panier
     */
    public function setModePaiement($modePaiement)
    {
        $this->modePaiement = $modePaiement;

        return $this;
    }

    /**
     * Get modePaiement
     *
     * @return string
     */
    public function getModePaiement()
    {
        return $this->modePaiement;
    }

    /**
     * Set modeLivraison
     *
     * @param string $modeLivraison
     *
     * @return Panier
     */
    public function setModeLivraison($modeLivraison)
    {
        $this->modeLivraison = $modeLivraison;

        return $this;
    }

    /**
     * Get modeLivraison
     *
     * @return string
     */
    public function getModeLivraison()
    {
        return $this->modeLivraison;
    }

    /**
     * Set estLivre
     *
     * @param boolean $estLivre
     *
     * @return Panier
     */
    public function setEstLivre($estLivre)
    {
        $this->estLivre = $estLivre;

        return $this;
    }

    /**
     * Get estLivre
     *
     * @return boolean
     */
    public function getEstLivre()
    {
        return $this->estLivre;
    }

    /**
     * Set estPaye
     *
     * @param boolean $estPaye
     *
     * @return Panier
     */
    public function setEstPaye($estPaye)
    {
        $this->estPaye = $estPaye;

        return $this;
    }

    /**
     * Get estPaye
     *
     * @return boolean
     */
    public function getEstPaye()
    {
        return $this->estPaye;
    }

    /**
     * Set idUser
     *
     * @param User $idUser
     *
     * @return Panier
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