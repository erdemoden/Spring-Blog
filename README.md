# MII Websitesi Backend Kodlarıdır 
bu website reddit benzeri bir sosyal medya websitesidir kullanıcılar blog sayfası açabilir blog sayfalarına adminler tanımlayabilir blogları takip edebilir bloglarda post paylaşıp diğerlerin postlarını beğenip yorum yapabilir. Blogların adminleri ise postlar onların olmasa bile postları isterlers silebilir. Bu Websitesini denemelkk için : https://mii-front.herokuapp.com sitesine girebilirsiniz. Herokuda sorun olduğu için ilk seferde açılmazsa ikinci sefer açılacaktır.

# Backend Tarafında Kullanılanlar 
backend tarafında spring boot kullanıldı database için mysql seçildi database ilişkileri hibernate ile sağlandı.Kullanıcı girişş işlemi sonrası gelen mail kodu redis kullanılarak saklandı. React.js frontend tarafında session tutmak için backend tarafında spring security ile jwt entegrasyonu yapıldı.

# Uygulamadan Resimler

![deneme-min](https://github.com/erdemoden/Spring-Blog/assets/53904841/8b2b2a19-8b03-42ea-a3eb-371210094ba2)

kullanıcı giriş kısımı 

![deneme2](https://github.com/erdemoden/Spring-Blog/assets/53904841/c8cdf554-2e04-49cb-9de6-960670ef7011)

kullanıcı post listeleme kısımı beğenme ve yorumları görme

<img width="744" alt="diagram" src="https://github.com/erdemoden/Spring-Blog/assets/53904841/1568e576-089f-4961-82ce-ef8a8c07d6c4">

database diagramı
