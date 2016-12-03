package helloworld;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import javafx.scene.control.IndexRange;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.LongStream;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

public class HelloServer {
    private int port = 50051;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port).addService(new HrImpl()).build().start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args)
            throws IOException, InterruptedException {
        final HelloServer server = new HelloServer();
        server.start();
        server.blockUntilShutdown();
    }

    private class HrImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(Helloworld.HelloRequest request,
                             io.grpc.stub.StreamObserver<Helloworld.HelloReply> responseObserver) {
            Helloworld.HelloReply helloReply = Helloworld.HelloReply.newBuilder().build();
            responseObserver.onNext(helloReply);
            responseObserver.onCompleted();
        }

        @Override
        public void listSayHello(Helloworld.HelloRequest request,
                                 io.grpc.stub.StreamObserver<Helloworld.HelloReply> responseObserver) {
            try {
                LongStream.range(1, 10).forEach(i -> {
                    responseObserver.onNext(Helloworld.HelloReply.newBuilder().build());
                });
            } catch (Throwable e) {
                responseObserver.onError(e);
            } finally {
                responseObserver.onCompleted();
            }
        }

    }
}
