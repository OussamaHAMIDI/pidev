<?php

namespace TombolaBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use UserBundle\Entity\User;

/**
 * InscriTombola
 *
 * @ORM\Table(name="inscri_tombola", indexes={@ORM\Index(name="id_tombola", columns={"id_tombola"}), @ORM\Index(name="id_gagnant", columns={"id_gagnant"})})
 * @ORM\Entity(repositoryClass="TombolaBundle\Repository\InscriRepository")
 */
class InscriTombola
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_inscri", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idInscri;

    /**
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_gagnant", referencedColumnName="id")
     * })
     */
    private $idGagnant;

    /**
     * @var Tombola
     *
     * @ORM\ManyToOne(targetEntity="Tombola")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_tombola", referencedColumnName="id_tombola", onDelete="CASCADE")
     * })
     */
    private $idTombola;

    /**
     * @var \DateTime
     *
     * @ORM\COlumn(name="date_inscri",type="datetime", nullable=true)
     */
    private $dateInscri;

    /**
     * InscriTombola constructor.
     */
    public function __construct()
    {
        $this->dateInscri = new \DateTime();
    }


    /**
     * Get idInscri
     *
     * @return integer
     */
    public function getIdInscri()
    {
        return $this->idInscri;
    }

    /**
     * Set idGagnant
     *
     * @param User $idGagnant
     *
     * @return InscriTombola
     */
    public function setIdGagnant(User $idGagnant = null)
    {
        $this->idGagnant = $idGagnant;

        return $this;
    }

    /**
     * Get idGagnant
     *
     * @return User
     */
    public function getIdGagnant()
    {
        return $this->idGagnant;
    }

    /**
     * Set idTombola
     *
     * @param Tombola $idTombola
     *
     * @return InscriTombola
     */
    public function setIdTombola(Tombola $idTombola = null)
    {
        $this->idTombola = $idTombola;

        return $this;
    }

    /**
     * Get idTombola
     *
     * @return Tombola
     */
    public function getIdTombola()
    {
        return $this->idTombola;
    }

    /**
     * @return \DateTime
     */
    public function getDateInscri()
    {
        return $this->dateInscri;
    }

    /**
     * @param \DateTime $dateInscri
     */
    public function setDateInscri($dateInscri)
    {
        $this->dateInscri = $dateInscri;
    }



}
