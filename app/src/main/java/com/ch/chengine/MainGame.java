package com.ch.chengine;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.ch.examples.Aviao;
import com.ch.jogo.Background1;
import com.ch.jogo.Background2;
import com.ch.jogo.Background3;
import com.ch.jogo.NomeJogo;
import com.ch.jogo.Passaro1;
import com.ch.opengl.OpenGlDrawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by charleston on 05/02/15.
 */
public class MainGame{

    public DrawableGroup[] groups;
    public int[] posicoes = new int[3];
    public int indice = 0;
    public static float cameraX = 0;
    public static float cameraY = 0;
    public Sprite bg1;
    public Sprite bg2;

    public Sprite background1P1;
    public Sprite background1P2;
    public Sprite backgroundt;
    public Sprite background2P1;
    public Sprite background2P2;
    public Aviao aviao;
    public Sprite passaro;
    public float[] posicoesEventos = new float[]{};
    public int indiceEvento = 0;
    public Random gerador = new Random();
    public List<Sprite> vaiReaparecer = new ArrayList<Sprite>();
    public boolean bateu = false;
    public MainGame(){

        //Grupos servem para agrupar drawables que usam o mesmo atlas de imagens
        groups = new DrawableGroup[4];
        posicoes[0] = 400;
        posicoes[1] = 800;
        posicoes[2] = 1200;

        //Crio cada grupo com seu atlas de imagens
        groups[0] = new DrawableGroup(R.drawable.fundos);
        groups[1] = new DrawableGroup(R.drawable.personagens);
        //groups[2] = new DrawableGroup(R.drawable.buttons);
        groups[2] = new DrawableGroup(R.drawable.collision_atlas);
        groups[3] = new DrawableGroup(R.drawable.aviao);


        //Adiciono cada sprite criado ao grupo que contem as imagens dele
        background1P1 = new Background1();
        background1P2 = new Background1();
        backgroundt = new Background2();
        background2P1 = new Background3();
        background2P2 = new Background3();

        bg1 = background1P1;
        bg2 = background1P2;
        bg2.x = bg1.width;
        posicoesEventos = new float[]{Dimensions.screenWidth,Dimensions.screenWidth*2, Dimensions.screenWidth*3};
        aviao = new Aviao();
        //passaro = new Passaro1();

        groups[0].add(backgroundt);
        groups[0].add(background2P1);
        groups[0].add(background2P2);

        groups[0].add(background1P1);
        groups[0].add(background1P2);
        groups[1].add(aviao);
        //groups[1].add(passaro);
        //groups[2].add(new Button());


        //Mostrar areas de colisão
        groups[2].add(aviao.rect);
        //groups[3].add(((ExampleSprite2)groups[1].drawables.get(1)).rect);

        NomeJogo imagem = new NomeJogo();
        imagem.x = 10;
        imagem.y = 10;
        imagem.width = 100;
        imagem.height = 100;
        groups[3].add(imagem);

    }

    public void beforeRender(){
        if(!bateu){
            cameraX = cameraX + Dimensions.unit*2;
            aviao.x = aviao.x + Dimensions.unit*2;

            aviao.aceleracaoY += 2; // gravidade
            aviao.aceleracaoY *= 0.94;// atrito
            aviao.y -= aviao.aceleracaoY;//mover objeto
            if(aviao.y < 10) {
                aviao.y = 10;
                aviao.aceleracaoY = -aviao.aceleracaoY;
            }


            //Log.d("Quanto a camera ja andou:", cameraX+"");

            //O que acontece quando um background sai para fora da tela
            if((bg1.x+ bg1.width)<cameraX){//quando o bg1 sair para fora da tela
                bg1.x = bg1.x + bg1.width*2;//avanço o bg1 da posição que eles está mais 2 * seu tamanho
                Log.d("backgroundp1", "jump");
            }
            if((bg2.x+ bg2.width)<cameraX){
                bg2.x = bg2.x + bg2.width*2;
                Log.d("backgroundp2", "jump");
            }
            for(int i = 0; i < vaiReaparecer.size(); i++){
                if(vaiReaparecer.get(i).getX()<cameraX){
                    vaiReaparecer.get(i).x = vaiReaparecer.get(i).getX() + Dimensions.screenWidth;
                }
            }
            /*if(passaro.x<cameraX){
                passaro.x = passaro.x + Dimensions.screenWidth;// + gerador.nextInt(2*(int)Dimensions.screenWidth);
            }*/

            //Eventos
            if(indiceEvento< posicoesEventos.length){//se ainda existem eventos
                if(cameraX> posicoesEventos[indiceEvento]){//se a camera passar da posicao do evento
                    if(indiceEvento == 0) {//se o evento for o primeiro
                        Log.i("evento", indiceEvento+"");
                        backgroundt.x = bg1.x;
                        bg1 = backgroundt;

                        background2P2.x = bg2.x;
                        bg2 = background2P2;
                    }
                    else if(indiceEvento == 1){
                        Log.i("evento", indiceEvento+"");
                        background2P1.x = bg1.x;
                        bg1 = background2P1;
                        passaro = new Passaro1();
                        passaro.setDimensions();
                        groups[1].add(passaro);
                        groups[2].add(((Passaro1)passaro).rect);
                        ((Passaro1)passaro).rect.setDimensions();
                        passaro.x = cameraX+Dimensions.screenWidth;
                        vaiReaparecer.add(passaro);
                    }
                    indiceEvento = indiceEvento + 1;
                }
            }
            if(passaro!=null){
                if(Collision.testRectCollision(aviao, (Collidable)passaro)){
                    Log.d("Colisão", "Eles colidiram");
                    bateu = true;
                    aviao.bateu();
                }
            }
            Log.d("posicoes", cameraX+" - "+aviao.x);
        }

    }
    public boolean inversor;
    public void onTouch(MotionEvent event){

        //aviao seguir o dedo
        //groups[1].drawables.get(0).x = event.getX();
        //groups[1].drawables.get(0).y = -(event.getY()-Dimensions.screenHeight);

        //############################################################################
        //aviao sobe quando toca
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
           aviao.aceleracaoY = -20;
        }

        //############################################################################

        //aviao sobre tocando acima da metade da tela e desce abaixo
        //if(event.getY()<Dimensions.screenHeight/2)
        //    groups[1].drawables.get(0).y +=5;
        //else{
        //    groups[1].drawables.get(0).y -=5;
        //}

        //############################################################################

        //aviao sobe quando toca, quando toca novamente, desce
        //if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
        //    if (inversor)
        //        groups[1].drawables.get(0).y -= 20;
        //    else
        //        groups[1].drawables.get(0).y += 20;
        //    inversor = !inversor;
        //}

        /*if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            Log.d("touch action index", event.getActionIndex()+"");
            Log.d("touch counter", event.getPointerCount()+"");
            Log.d("touch ID", event.getPointerId(event.getActionIndex())+"");
        }
        else if(event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
            Log.d("touch action index", event.getActionIndex()+"");
            Log.d("touch counter", event.getPointerCount()+"");
            Log.d("touch ID", event.getPointerId(event.getActionIndex())+"");
            int id = event.getPointerId(event.getActionIndex());
            Log.d("x - y:", event.getX(id)+" - "+event.getY(id));
        }*/
    }



    public void adjustSizes(){
        for (int i = 0; i < groups.length; i++) {
            for(OpenGlDrawable drawable: groups[i].drawables)
                drawable.setDimensions();
        }

    }
    public void onSaveInstanceState(Bundle bundle){
        bundle.putBoolean("statusSaved", true);
    }

    public void onRestoreInstanceState(Bundle bundle){
        Log.i("restore", bundle.getBoolean("statusSaved")+"");
    }
}
