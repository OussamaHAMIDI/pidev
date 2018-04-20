<?php

// LogoutListener.php - Change the namespace according to the location of this class in your bundle
namespace UserBundle\Listeners;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Http\Logout\LogoutHandlerInterface;
use FOS\UserBundle\Model\UserManagerInterface;

class LogoutListener implements LogoutHandlerInterface
{
    protected $userManager;

    public function __construct(UserManagerInterface $userManager)
    {
        $this->userManager = $userManager;
    }

    public function logout(Request $Request, Response $Response, TokenInterface $Token)
    {
        // ..
        // Here goes the logic that you want to execute when the user logouts

        $user = $Token->getUser();
        $user->setEtat("Disconnected");
        $this->userManager->updateUser($user);

        // ..

        // The following example will create the logs.txt file in the /web directory of your project
        $time = new \DateTime();
        $result = $time->format('Y-m-d H:i:s');

        $txt = $Token->getUsername().' logout executed At : '.$result;
        $file = file_put_contents('logs.txt', $txt.PHP_EOL , FILE_APPEND | LOCK_EX);

    }
}