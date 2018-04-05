<?php

namespace UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use FOS\UserBundle\Model\User as BaseUser;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity as UniqueEntity;

/**
 * User
 *
 * @ORM\Table(name="`user`", uniqueConstraints={@ORM\UniqueConstraint(name="username", columns={"username"}), @ORM\UniqueConstraint(name="username_canonical", columns={"username_canonical"}), @ORM\UniqueConstraint(name="email", columns={"email"}), @ORM\UniqueConstraint(name="email_canonical", columns={"email_canonical"}), @ORM\UniqueConstraint(name="confirmation_token", columns={"confirmation_token"})})
 * @ORM\HasLifecycleCallbacks
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
     * @Assert\Type(
     *     type= "integer",
     *     message="Le numéro de telephone doit contenir 8 chiffres ou plus")
     */
    private $tel;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=100, nullable=false)
     * @Assert\Regex(
     *     pattern = "/^([a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+ [a-zA-Z]+)$/i",
     *     message="Votre nom ne peut pas contenir des chiffres")
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="prenom", type="string", length=50, nullable=false)
     *  * @Assert\Regex(
     *     pattern = "/^([a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+ [a-zA-Z]+)$/i",
     *     message="Votre prenom ne peut pas contenir des chiffres")
     */
    private $prenom;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_naissance", type="date", nullable=false)
     * @Assert\LessThan("-17 years", message="Vous etes trop jeune")
     * @Assert\GreaterThan("-100 years", message="Vous etes trop grand xD")
     */
    private $dateNaissance;

    /**
     * @var string
     *
     * @ORM\Column(name="sexe", type="string", length=10, nullable=false)
     */
    private $sexe;

    /**
     * @var mixed
     *
     * @ORM\Column(name="photo_profil", type="blob", length=16777215, nullable=false)
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
     * @Assert\File( maxSize = "1024k", mimeTypesMessage = "Taille de l'image max < 1024 Ko")
     * @ORM\Column(name="path_photo_profil", type="string", length=255, nullable=false)
     */
    private $pathPhotoProfil;


    /**
     * @var string
     *
     * @Assert\File( maxSize = "1024k", mimeTypesMessage = "Taille de l'image max < 1024 Ko")
     * @ORM\Column(name="path_photo_permis", type="string", length=255, nullable=true)
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

    }


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
     * @return string
     */
    public function getPathPhotoProfil()
    {
        return $this->pathPhotoProfil;
    }

    /**
     * @param string $pathPhotoProfil
     */
    public function setPathPhotoProfil(string $pathPhotoProfil)
    {
        $this->pathPhotoProfil = $pathPhotoProfil;
    }

    /**
     * @return string
     */
    public function getPathPhotoPermis()
    {
        return $this->pathPhotoPermis;
    }

    /**
     * @param string $pathPhotoPermis
     */
    public function setPathPhotoPermis(string $pathPhotoPermis): void
    {
        $this->pathPhotoPermis = $pathPhotoPermis;
    }

    /**
     * @return mixed
     */
    public function getPhotoProfil()
    {
        return $this->photoProfil;
    }

    /**
     * @param mixed $photoProfil
     */
    public function setPhotoProfil($photoProfil): void
    {
        $this->photoProfil = $photoProfil;
    }




    /**
     * @ORM\PostLoad()
     */
    public function postLoad()
    {

        $this->updateAt = new \DateTime();
    }

    public function getUploadRootDir()
    {
        return dirname(__DIR__, 4).'/uploads';
    }

    public function getAbsolutePathPhotoProfil()
    {
        return null === $this->pathPhotoProfil ? null : $this->getUploadRootDir().'/'.$this->pathPhotoProfil;
    }

    public function getAssetPath()
    {
        return 'uploads/'.$this->pathPhotoProfil;
    }
    /******************************************************************************************************************/

    private $fileProfil;



    /**
     * @ORM\PrePersist()
     * @ORM\PreUpdate()
     */
    public function preUpload()
    {
        $this->tempFile = $this->getAbsolutePathPhotoProfil();
        $this->oldFile = $this->pathPhotoProfil;
        $this->updateAt = new \DateTime();


        if (null !== $this->fileProfil)
            $this->pathPhotoProfil = sha1(uniqid(mt_rand(),true)).'.'.$this->fileProfil->guessExtension();
    }

    /**
     * @ORM\PostPersist()
     * @ORM\PostUpdate()
     */
    public function upload()
    {
        if (null !== $this->fileProfil) {
            $this->fileProfil->move($this->getUploadRootDir(),$this->pathPhotoProfil);
            unset($this->fileProfil);

            if ($this->oldFile != null) unlink($this->tempFile);
        }
    }

    /**
     * @ORM\PreRemove()
     */
    public function preRemoveUpload()
    {
        $this->tempFile = $this->getAbsolutePathPhotoProfil();
    }

    /**
     * @ORM\PostRemove()
     */
    public function removeUpload()
    {
        if (file_exists($this->tempFile)) unlink($this->tempFile);
    }


}
