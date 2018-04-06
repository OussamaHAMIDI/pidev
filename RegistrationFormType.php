<?php

/*
 * This file is part of the FOSUserBundle package.
 *
 * (c) FriendsOfSymfony <http://friendsofsymfony.github.com/>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

namespace FOS\UserBundle\Form\Type;

use FOS\UserBundle\Util\LegacyFormHelper;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\BirthdayType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\CountryType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class RegistrationFormType extends AbstractType
{
    /**
     * @var string
     */
    private $class;

    /**
     * @param string $class The User class name
     */
    public function __construct($class)
    {
        $this->class = $class;
    }

    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('email', EmailType::class, array('label' => 'Email', 'translation_domain' => 'FOSUserBundle'))
            ->add('username', null, array('label' => "Nom d'utilisateur", 'translation_domain' => 'FOSUserBundle'))
            ->add('plainPassword', RepeatedType::class, array(
                'type' => PasswordType::class,
                'options' => array(
                    'translation_domain' => 'FOSUserBundle',
                    'attr' => array(
                        'autocomplete' => 'new-password',
                    ),
                ),
                'first_options' => array('label' => 'form.password'),
                'second_options' => array('label' => 'form.password_confirmation'),
                'invalid_message' => 'fos_user.password.mismatch',
            ))

            ->add('nom', null, array('label' => 'Nom', 'attr' => array(
                'class' => 'form-control')))
            ->add('prenom', null, array('label' => 'Prenom', 'attr' => array(
                'class' => 'form-control')))
            ->add('dateNaissance', DateType::class, array('label' => 'Date De Naissance', 'attr' => array(
                'class' => 'form-control'),
                'html5' => true,'data' => new \DateTime('01/01/1995'),
                'widget' => 'single_text'))

            ->add('sexe', ChoiceType::class, array(
                'choices' => array(
                    'Homme' => 'Homme',
                    'Femme' => 'Femme',
                ), 'label' => 'Sexe', 'attr' => array(
                    'class' => 'form-control')))

            ->add('type', ChoiceType::class, array(
                'choices' => array(
                    'Artisan' => 'Artisan',
                    'Client' => 'Client',
                ), 'label' => 'Type', 'attr' => array(
                    'class' => 'form-control')))

            ->add('adresse', null, array('label' => 'Adresse', 'attr' => array(
                'class' => 'form-control')))
            ->add('tel', null, array('label' => 'Numéro de Téléphone', 'attr' => array(
                'class' => 'form-control')))
            ->add('pathPhotoProfil', FileType::class, array('required'=>true,'label' => 'Photo de profil','attr'   =>  array(
                'class'   => 'form-control')))
            ->add('pathPhotoPermis', FileType::class, array('required'=>false,'label' => 'Photo de permis','attr'   =>  array(
                'class'   => 'form-control')));
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => $this->class,
            'csrf_token_id' => 'registration',
        ));
    }

    // BC for SF < 3.0

//    /**
//     * {@inheritdoc}
//     */
//    public function getName()
//    {
//        return $this->getBlockPrefix();
//    }
//
//    /**
//     * {@inheritdoc}
//     */
//    public function getBlockPrefix()
//    {
//        return 'fos_user_registration';
//    }
}
