package ru.rtln.gatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Request logging filter.
 *
 * @author Darya Stupak
 */
@Slf4j
@Component
public class RouteInfoFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Path requested -> {}", exchange.getRequest().getURI());
        return chain.filter(exchange);
    }
}

/*
$ useradd farm-user -m
$ passwd farm-user
$ usermod -a -G sudo pogreb
$ su farm-user
$ cd
$ pwd
$ sudo apt-get update && sudo apt-get install apt-transport-https ca-certificates curl gnupg lsb-release
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
$ echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
$ sudo apt-get update
$ apt-cache madison docker-ce
$ sudo apt-get install docker-ce=5:20.10.13~3-0~ubuntu-jammy
$ sudo systemctl status docker
$ sudo usermod -aG docker farm-user
$ sudo systemctl restart docker
$ docker ps
 */