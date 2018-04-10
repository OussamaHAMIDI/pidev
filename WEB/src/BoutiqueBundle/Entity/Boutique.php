<?php

namespace BoutiqueBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use UserBundle\Entity\User;



/**
 * Boutique
 *
 * @ORM\Table(name="boutique", indexes={@ORM\Index(name="id_user", columns={"id_user"})})
 * @ORM\Entity
 */
class Boutique
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
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=50, nullable=false)
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="adresse", type="string", length=100, nullable=true)
     */
    private $adresse;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_creation", type="datetime", nullable=false)
     */
    private $dateCreation ;

    /**
     * @var float
     *
     * @ORM\Column(name="longitude", type="float", precision=10, scale=0, nullable=false)
     */
    private $longitude;

    /**
     * @var float
     *
     * @ORM\Column(name="altitude", type="float", precision=10, scale=0, nullable=false)
     */
    private $altitude;

    /**
     * @var string
     *
     * @ORM\Column(name="photo", type="blob", length=16777215, nullable=false)
     */
    private $photo;

    /**
     * @var string
     *
     * @ORM\Column(name="path_photo", type="string", length=180, nullable=true)
     */
    private $pathPhoto;

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
     * Set nom
     *
     * @param string $nom
     *
     * @return Boutique
     */
    public function setNom($nom)
    {
        $this->nom = $nom;

        return $this;
    }

    /**
     * Get nom
     *
     * @return string
     */
    public function getNom()
    {
        return $this->nom;
    }

    /**
     * Set adresse
     *
     * @param string $adresse
     *
     * @return Boutique
     */
    public function setAdresse($adresse)
    {
        $this->adresse = $adresse;

        return $this;
    }

    /**
     * Get adresse
     *
     * @return string
     */
    public function getAdresse()
    {
        return $this->adresse;
    }

    /**
     * Set dateCreation
     *
     * @param \DateTime $dateCreation
     *
     * @return Boutique
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
     * Set longitude
     *
     * @param float $longitude
     *
     * @return Boutique
     */
    public function setLongitude($longitude)
    {
        $this->longitude = $longitude;

        return $this;
    }

    /**
     * Get longitude
     *
     * @return float
     */
    public function getLongitude()
    {
        return $this->longitude;
    }

    /**
     * Set altitude
     *
     * @param float $altitude
     *
     * @return Boutique
     */
    public function setAltitude($altitude)
    {
        $this->altitude = $altitude;

        return $this;
    }

    /**
     * Get altitude
     *
     * @return float
     */
    public function getAltitude()
    {
        return $this->altitude;
    }

    /**
     * Set photo
     *
     * @param string $photo
     *
     * @return Boutique
     */
    public function setPhoto($photo)
    {
        $this->photo = $photo;

        return $this;
    }

    /**
     * Get photo
     *
     * @return string
     */
    public function getPhoto()
    {
        return $this->photo;
    }

    /**
     * Set idUser
     *
     * @param User $idUser
     *
     * @return Boutique
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

    /**
     * Set pathPhoto
     *
     * @param string $pathPhoto
     *
     * @return Boutique
     */
    public function setPathPhoto($pathPhoto)
    {
        $this->pathPhoto = $pathPhoto;

        return $this;
    }

    /**
     * Get pathPhoto
     *
     * @return string
     */
    public function getPathPhoto()
    {
        return $this->pathPhoto;
    }
}
