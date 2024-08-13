import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IAvaliacao } from 'app/entities/avaliacao/avaliacao.model';
import { AvaliacaoService } from 'app/entities/avaliacao/service/avaliacao.service';
import { IAvaliacaoContador } from '../avaliacao-contador.model';
import { AvaliacaoContadorService } from '../service/avaliacao-contador.service';
import { AvaliacaoContadorFormService } from './avaliacao-contador-form.service';

import { AvaliacaoContadorUpdateComponent } from './avaliacao-contador-update.component';

describe('AvaliacaoContador Management Update Component', () => {
  let comp: AvaliacaoContadorUpdateComponent;
  let fixture: ComponentFixture<AvaliacaoContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let avaliacaoContadorFormService: AvaliacaoContadorFormService;
  let avaliacaoContadorService: AvaliacaoContadorService;
  let contadorService: ContadorService;
  let avaliacaoService: AvaliacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AvaliacaoContadorUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AvaliacaoContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AvaliacaoContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    avaliacaoContadorFormService = TestBed.inject(AvaliacaoContadorFormService);
    avaliacaoContadorService = TestBed.inject(AvaliacaoContadorService);
    contadorService = TestBed.inject(ContadorService);
    avaliacaoService = TestBed.inject(AvaliacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contador query and add missing value', () => {
      const avaliacaoContador: IAvaliacaoContador = { id: 456 };
      const contador: IContador = { id: 139 };
      avaliacaoContador.contador = contador;

      const contadorCollection: IContador[] = [{ id: 7174 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ avaliacaoContador });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Avaliacao query and add missing value', () => {
      const avaliacaoContador: IAvaliacaoContador = { id: 456 };
      const avaliacao: IAvaliacao = { id: 16234 };
      avaliacaoContador.avaliacao = avaliacao;

      const avaliacaoCollection: IAvaliacao[] = [{ id: 4075 }];
      jest.spyOn(avaliacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: avaliacaoCollection })));
      const additionalAvaliacaos = [avaliacao];
      const expectedCollection: IAvaliacao[] = [...additionalAvaliacaos, ...avaliacaoCollection];
      jest.spyOn(avaliacaoService, 'addAvaliacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ avaliacaoContador });
      comp.ngOnInit();

      expect(avaliacaoService.query).toHaveBeenCalled();
      expect(avaliacaoService.addAvaliacaoToCollectionIfMissing).toHaveBeenCalledWith(
        avaliacaoCollection,
        ...additionalAvaliacaos.map(expect.objectContaining),
      );
      expect(comp.avaliacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const avaliacaoContador: IAvaliacaoContador = { id: 456 };
      const contador: IContador = { id: 1659 };
      avaliacaoContador.contador = contador;
      const avaliacao: IAvaliacao = { id: 20913 };
      avaliacaoContador.avaliacao = avaliacao;

      activatedRoute.data = of({ avaliacaoContador });
      comp.ngOnInit();

      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.avaliacaosSharedCollection).toContain(avaliacao);
      expect(comp.avaliacaoContador).toEqual(avaliacaoContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvaliacaoContador>>();
      const avaliacaoContador = { id: 123 };
      jest.spyOn(avaliacaoContadorFormService, 'getAvaliacaoContador').mockReturnValue(avaliacaoContador);
      jest.spyOn(avaliacaoContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacaoContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avaliacaoContador }));
      saveSubject.complete();

      // THEN
      expect(avaliacaoContadorFormService.getAvaliacaoContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(avaliacaoContadorService.update).toHaveBeenCalledWith(expect.objectContaining(avaliacaoContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvaliacaoContador>>();
      const avaliacaoContador = { id: 123 };
      jest.spyOn(avaliacaoContadorFormService, 'getAvaliacaoContador').mockReturnValue({ id: null });
      jest.spyOn(avaliacaoContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacaoContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avaliacaoContador }));
      saveSubject.complete();

      // THEN
      expect(avaliacaoContadorFormService.getAvaliacaoContador).toHaveBeenCalled();
      expect(avaliacaoContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvaliacaoContador>>();
      const avaliacaoContador = { id: 123 };
      jest.spyOn(avaliacaoContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacaoContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(avaliacaoContadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContador', () => {
      it('Should forward to contadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(contadorService, 'compareContador');
        comp.compareContador(entity, entity2);
        expect(contadorService.compareContador).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAvaliacao', () => {
      it('Should forward to avaliacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(avaliacaoService, 'compareAvaliacao');
        comp.compareAvaliacao(entity, entity2);
        expect(avaliacaoService.compareAvaliacao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
