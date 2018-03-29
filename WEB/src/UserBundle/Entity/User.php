<?php

namespace UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use FOS\UserBundle\Model\User as BaseUser;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * User
 *
 * @ORM\Table(name="`user`", uniqueConstraints={@ORM\UniqueConstraint(name="username", columns={"username"}), @ORM\UniqueConstraint(name="username_canonical", columns={"username_canonical"}), @ORM\UniqueConstraint(name="email", columns={"email"}), @ORM\UniqueConstraint(name="email_canonical", columns={"email_canonical"}), @ORM\UniqueConstraint(name="confirmation_token", columns={"confirmation_token"})})
 * @ORM\Entity
 */
class User extends BaseUser
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    protected $id;


    /**
     * @var string
     *
     * @ORM\Column(name="type", type="string", nullable=false)
     */
    private $type;

    /**
     * @var string
     *
     * @ORM\Column(name="etat", type="string", nullable=false)
     */
    private $etat;

    /**
     * @var string
     *
     * @ORM\Column(name="adresse", type="string", length=150, nullable=false)
     */
    private $adresse;

    /**
     * @var string
     *
     * @ORM\Column(name="tel", type="string", length=15, nullable=false)
     */
    private $tel;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=100, nullable=false)
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="prenom", type="string", length=50, nullable=false)
     */
    private $prenom;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_naissance", type="date", nullable=false)
     */
    private $dateNaissance;

    /**
     * @var string
     *
     * @ORM\Column(name="sexe", type="string", length=10, nullable=false)
     */
    private $sexe;

    /**
     * @var string
     *
     * @ORM\Column(name="photo_profil", type="blob", length=16777215, nullable=true)
     */
    private $photoProfil;

    /**
     * @var string
     *
     * @ORM\Column(name="photo_permis", type="blob", length=16777215, nullable=true)
     */
    private $photoPermis;

    /**
     * @var string
     *
     * @ORM\Column(name="path_photo_profil", type="string", length=180, nullable=true)
     */
    private $pathPhotoProfil;

    /**
     * @var string
     *
     * @ORM\Column(name="path_photo_permis", type="string", length=180, nullable=true)
     */
    private $pathPhotoPermis;

    /**
     * User constructor.
     */
    public function __construct()
    {
        parent::__construct();


        if($this->enabled){
            $this->etat = "Active";
        }else{
            $this->etat = "Pending";
        }

        $this->enabled = true;

    }

//
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
     * Set type
     *
     * @param string $type
     *
     */
    public function setType($type)
    {

        if($type === "Artisan"){
            $this->roles = array('ROLE_ARTISAN');
        }
        else if($type === "Client"){
            $this->addRole('ROLE_CLIENT');
        }
        else{
            $this->addRole('ROLE_ADMINISTRATEUR');
        }
        $this->enabled = true;

        $this->type = $type;
    }

    /**
     * Get type
     *
     * @return string
     */
    public function getType()
    {
        return $this->type;
    }

    /**
     * Set etat
     *
     * @param string $etat
     *
     * @return User
     */
    public function setEtat($etat)
    {
        $this->etat = $etat;

        return $this;
    }

    /**
     * Get etat
     *
     * @return string
     */
    public function getEtat()
    {
        return $this->etat;
    }

    /**
     * Set adresse
     *
     * @param string $adresse
     *
     * @return User
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
     * Set tel
     *
     * @param string $tel
     *
     * @return User
     */
    public function setTel($tel)
    {
        $this->tel = $tel;

        return $this;
    }

    /**
     * Get tel
     *
     * @return string
     */
    public function getTel()
    {
        return $this->tel;
    }

    /**
     * Set nom
     *
     * @param string $nom
     *
     * @return User
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
     * Set prenom
     *
     * @param string $prenom
     *
     * @return User
     */
    public function setPrenom($prenom)
    {
        $this->prenom = $prenom;

        return $this;
    }

    /**
     * Get prenom
     *
     * @return string
     */
    public function getPrenom()
    {
        return $this->prenom;
    }

    /**
     * Set dateNaissance
     *
     * @param \DateTime $dateNaissance
     *
     * @return User
     */
    public function setDateNaissance($dateNaissance)
    {
        $this->dateNaissance = $dateNaissance;

        return $this;
    }

    /**
     * Get dateNaissance
     *
     * @return \DateTime
     */
    public function getDateNaissance()
    {
        return $this->dateNaissance;
    }

    /**
     * Set sexe
     *
     * @param string $sexe
     *
     * @return User
     */
    public function setSexe($sexe)
    {
        $this->sexe = $sexe;

        return $this;
    }

    /**
     * Get sexe
     *
     * @return string
     */
    public function getSexe()
    {
        return $this->sexe;
    }

    /**
     * Set photoProfil
     *
     * @param string $photoProfil
     *
     * @return User
     */
    public function setPhotoProfil($photoProfil)
    {
        $this->photoProfil = $photoProfil;

        return $this;
    }

    /**
     * Get photoProfil
     *
     * @return string
     */
    public function getPhotoProfil()
    {
        return $this->photoProfil;
    }

    /**
     * Set photoPermis
     *
     * @param string $photoPermis
     *
     * @return User
     */
    public function setPhotoPermis($photoPermis)
    {
        $this->photoPermis = $photoPermis;

        return $this;
    }

    /**
     * Get photoPermis
     *
     * @return string
     */
    public function getPhotoPermis()
    {
        return $this->photoPermis;
    }

    /**
     * Set pathPhotoProfil
     *
     * @param string $pathPhotoProfil
     *
     * @return User
     */
    public function setPathPhotoProfil($pathPhotoProfil)
    {
        $this->pathPhotoProfil = $pathPhotoProfil;

        return $this;
    }

    /**
     * Get pathPhotoProfil
     *
     * @return string
     */
    public function getPathPhotoProfil()
    {
        return $this->pathPhotoProfil;
    }

    /**
     * Set pathPhotoPermis
     *
     * @param string $pathPhotoPermis
     *
     * @return User
     */
    public function setPathPhotoPermis($pathPhotoPermis)
    {
        $this->pathPhotoPermis = $pathPhotoPermis;

        return $this;
    }

    /**
     * Get pathPhotoPermis
     *
     * @return string
     */
    public function getPathPhotoPermis()
    {
        return $this->pathPhotoPermis;
    }
}
