FROM node:lts as build-stage

WORKDIR /app
COPY package*.json ./

RUN npm install
COPY . .
RUN npm run build-prod

FROM nginx:stable-alpine as production-stage

COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY ./infra/nginx/nginx.conf /etc/nginx/nginx.conf
COPY ./infra/https_certification/fullchain.pem /etc/https/fullchain.pem
COPY ./infra/https_certification/privkey.pem /etc/https/privkey.pem

COPY ./infra/nginx/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 80 443

CMD ["nginx", "-g", "daemon off;"]