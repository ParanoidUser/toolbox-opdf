FROM alpine:3.17.1 as builder
COPY ../opdf /build/
RUN apk add --no-cache gcompat \
    && mkdir /build/lib/ /build/lib64/ \
    && cp /lib/ld-musl-x86_64.so.1 \
          /lib/libgcompat.so.0 \
          /lib/libucontext.so.1 \
          /usr/lib/libobstack.so.1 \
          /lib/libz.so.1 \
       /build/lib/ \
    && cp /lib64/ld-linux-x86-64.so.2 /build/lib64/

FROM scratch
COPY --from=builder /build/ /
ENTRYPOINT ["/opdf"]