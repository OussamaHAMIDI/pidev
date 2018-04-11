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
 * @ORM\Entity(repositoryClass="UserBundle\Repository\UserRepository")
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
     * @Assert\File( maxSize="2M", mimeTypes={"image/png", "image/jpeg", "image/jpeg"} , mimeTypesMessage = "Veuillez choisir une image valide" ,
     *     maxSizeMessage="Taille maximale de l'image 2Mo")
     * @ORM\Column(name="path_photo_profil", type="string", length=255, nullable=false)
     */
    private $pathPhotoProfil;


    /**
     * @var string
     *
     * @Assert\File( maxSize="2M", mimeTypes={"image/png", "image/jpeg", "image/jpeg"} , mimeTypesMessage = "Veuillez choisir une image valide" ,
     *     maxSizeMessage="Taille maximale de l'image 2Mo")
     *
     * @ORM\Column(name="path_photo_permis", type="string", length=255, nullable=true)
     */
    private $pathPhotoPermis;


    public function setEnabled($boolean)
    {
        $this->enabled = (bool) $boolean;
        if($boolean) {
            $this->etat = "Active";
        } else {
            $this->etat = "Pending";
        }

        return $this;
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
     * {@inheritdoc}
     */
    public function setLastLogin(\DateTime $time = null)
    {
        $this->lastLogin = $time;
        $this->etat = "Connected";

        return $this;
    }

    /**
     * Set type
     *
     * @param string $type
     *
     */
    public function setType($type)
    {
        if ($type === "Artisan") {
            $this->roles = array('ROLE_ARTISAN');
        } else {
            if ($type === "Client") {
                $this->addRole('ROLE_CLIENT');
            } else {
                $this->addRole('ROLE_ADMINISTRATEUR');
            }
        }
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
    public function setPathPhotoPermis(string $pathPhotoPermis)
    {
        $this->pathPhotoPermis = $pathPhotoPermis;
    }

    /******************************************* pour les photos ***************************/


    public function getUploadRootDir()
    {
        return 'C:/xampp/htdocs/pidev/WEB/web/uploads';
//        return __dir__.'/../../../web/uploads';
//        return dirname(__DIR__, 4).'/uploads';
    }

    public function getAbsolutePathPhotoProfil()
    {
        return null === $this->pathPhotoProfil ? null : $this->getUploadRootDir().'/'.$this->pathPhotoProfil;
    }
    
    public function getAbsolutePathPhotoPermis()
    {
        return null === $this->pathPhotoPermis ? null : $this->getUploadRootDir().'/'.$this->pathPhotoPermis;
    }


    public $fileP;
    public $filePe;


    /**
     * @ORM\PrePersist()
     * @ORM\PreUpdate()
     */
    public function preUpload()
    {
        $t = $this->getAbsolutepathPhotoProfil();

        if (strlen($t) > strlen($this->getUploadRootDir()) + 41) {
            $this->tempFileP = substr($t, strlen($this->getUploadRootDir()) + 1);
        } else {
            $this->tempFileP = $this->getAbsolutepathPhotoProfil();
        }
        if($this->pathPhotoPermis != null){
            $t = $this->getAbsolutepathPhotoProfil();
            if (strlen($t) > strlen($this->getUploadRootDir()) + 41) {
                $this->tempFilePe = substr($t, strlen($this->getUploadRootDir()) + 1);
            } else {
                $this->tempFilePe = $this->getAbsolutePathPhotoPermis();
            }
        }
        
        $this->oldFileP = $this->pathPhotoProfil;

        $this->oldFilePe = $this->pathPhotoPermis;

        if (null !== $this->fileP) {
            $this->pathPhotoProfil = sha1(uniqid(mt_rand(), true)).'.'.$this->fileP->guessExtension();
        }
        if (null !== $this->filePe) {
            $this->pathPhotoPermis = sha1(uniqid(mt_rand(), true)).'.'.$this->filePe->guessExtension();
        }
    }

    /**
     * @ORM\PostPersist()
     * @ORM\PostUpdate()
     */
    public function upload()
    {
        if (null !== $this->fileP) {
            $this->fileP->move($this->getUploadRootDir(), $this->pathPhotoProfil);
            unset($this->fileP);

            if ($this->oldFileP != null) {
                unlink($this->tempFileP);
            }
        }
        if (null !== $this->filePe) {
            $this->filePe->move($this->getUploadRootDir(), $this->pathPhotoPermis);
            unset($this->filePe);

            if ($this->oldfilePe != null) {
                unlink($this->tempFilePe);
            }
        }
    }

    /**
     * @ORM\PreRemove()
     */
    public function preRemoveUpload()
    {
        $this->tempFileP = $this->getAbsolutepathPhotoProfil();
        $this->tempFilePe = $this->getAbsolutePathPhotoPermis();

    }

    /**
     * @ORM\PostRemove()
     */
    public function removeUpload()
    {
        if (fileP_exists($this->tempFileP)) {
            unlink($this->tempFileP);
        }
        if (fileP_exists($this->tempFilePe)) {
            unlink($this->tempFilePe);
        }
    }

}
