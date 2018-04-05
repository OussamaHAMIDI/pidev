<?php

namespace TombolaBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use UserBundle\Entity\User;

/**
 * InscriTombola
 *
 * @ORM\Table(name="tombola_participants", indexes={@ORM\Index(name="id_tombola", columns={"id_tombola"}), @ORM\Index(name="id_participant", columns={"id_participant"})})
 * @ORM\Entity(repositoryClass="TombolaBundle\Repository\TombolaParticipantsRepository")
 */
class TombolaParticipants
{
//    /**
//     * @var integer
//     *
//     * @ORM\Column(name="id", type="integer", nullable=false)
//     * @ORM\Id
//     * @ORM\GeneratedValue(strategy="IDENTITY")
//     */
//    private $id;

    /**
     * @var User
     *
     * @ORM\Id
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_participant", referencedColumnName="id", onDelete="CASCADE")
     * })
     */
    private $idParticipant;

    /**
     * @var Tombola
     *
     * @ORM\Id
     * @ORM\ManyToOne(targetEntity="TombolaBundle\Entity\Tombola")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_tombola", referencedColumnName="id", onDelete="CASCADE")
     * })
     */
    private $idTombola;

    /**
     * @var \DateTime
     *
     * @ORM\COlumn(name="date_inscription",type="datetime", nullable=false)
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
     * @return User
     */
    public function getIdGagnant(): User
    {
        return $this->idGagnant;
    }

    /**
     * @param User $idGagnant
     */
    public function setIdGagnant(User $idGagnant): void
    {
        $this->idGagnant = $idGagnant;
    }

    /**
     * @return Tombola
     */
    public function getIdTombola(): Tombola
    {
        return $this->idTombola;
    }

    /**
     * @param Tombola $idTombola
     */
    public function setIdTombola(Tombola $idTombola): void
    {
        $this->idTombola = $idTombola;
    }

    /**
     * @return \DateTime
     */
    public function getDateInscri(): \DateTime
    {
        return $this->dateInscri;
    }

    /**
     * @param \DateTime $dateInscri
     */
    public function setDateInscri(\DateTime $dateInscri): void
    {
        $this->dateInscri = $dateInscri;
    }

}
