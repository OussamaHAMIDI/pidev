<?php

namespace TombolaBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;

class TombolaEditType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('titre',TextType::class)
            ->add('description',TextareaType::class,['attr' => ['rows' => '5','style' => 'resize:vertical']])

//            ->add('dateAjout',null,array(
//                'attr'=>array('style'=>'display:none;'),
//                'label_attr'=>array('style'=>'display:none;') ) )


            ->add('dateTirage',DateTimeType::class, array('required' => true,
                'html5' => false,
                'widget' => 'single_text',
//                'attr' => ['readonly' => 'enabled'],
//                'input' => 'datetime', 'format' => 'yyyy-MM-dd  HH:mm:ss','data' => new \DateTime('now +7 days')
            ))
            ->add('file',FileType::class, array('required' => false, 'label' => 'Image du tombola',));


    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'TombolaBundle\Entity\Tombola'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'tombolabundle_tombola';
    }


}
