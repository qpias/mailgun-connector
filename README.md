mailgun-connector
=================

Simple Mule ESB connector for Mailgun (https://mailgun.com) REST API. Currently only has basic text email for single recipient.


    <mailgun:config domain="your-domain.mailgun.org" key="key-insert-your-key-here"/>

    <flow name="testFlow">
        <mailgun:my-processor from="your@mail.address" to="receiver@mail.address" subject="hello" message="insert-your-message-here" />
    </flow>

