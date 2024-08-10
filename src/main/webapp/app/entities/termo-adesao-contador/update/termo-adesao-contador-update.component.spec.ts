import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { ITermoDeAdesao } from 'app/entities/termo-de-adesao/termo-de-adesao.model';
import { TermoDeAdesaoService } from 'app/entities/termo-de-adesao/service/termo-de-adesao.service';
import { ITermoAdesaoContador } from '../termo-adesao-contador.model';
import { TermoAdesaoContadorService } from '../service/termo-adesao-contador.service';
import { TermoAdesaoContadorFormService } from './termo-adesao-contador-form.service';

import { TermoAdesaoContadorUpdateComponent } from './termo-adesao-contador-update.component';

describe('TermoAdesaoContador Management Update Component', () => {
  let comp: TermoAdesaoContadorUpdateComponent;
  let fixture: ComponentFixture<TermoAdesaoContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let termoAdesaoContadorFormService: TermoAdesaoContadorFormService;
  let termoAdesaoContadorService: TermoAdesaoContadorService;
  let contadorService: ContadorService;
  let termoDeAdesaoService: TermoDeAdesaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TermoAdesaoContadorUpdateComponent],
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
      .overrideTemplate(TermoAdesaoContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TermoAdesaoContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    termoAdesaoContadorFormService = TestBed.inject(TermoAdesaoContadorFormService);
    termoAdesaoContadorService = TestBed.inject(TermoAdesaoContadorService);
    contadorService = TestBed.inject(ContadorService);
    termoDeAdesaoService = TestBed.inject(TermoDeAdesaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contador query and add missing value', () => {
      const termoAdesaoContador: ITermoAdesaoContador = { id: 456 };
      const contador: IContador = { id: 20924 };
      termoAdesaoContador.contador = contador;

      const contadorCollection: IContador[] = [{ id: 14196 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ termoAdesaoContador });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TermoDeAdesao query and add missing value', () => {
      const termoAdesaoContador: ITermoAdesaoContador = { id: 456 };
      const termoDeAdesao: ITermoDeAdesao = { id: 13472 };
      termoAdesaoContador.termoDeAdesao = termoDeAdesao;

      const termoDeAdesaoCollection: ITermoDeAdesao[] = [{ id: 425 }];
      jest.spyOn(termoDeAdesaoService, 'query').mockReturnValue(of(new HttpResponse({ body: termoDeAdesaoCollection })));
      const additionalTermoDeAdesaos = [termoDeAdesao];
      const expectedCollection: ITermoDeAdesao[] = [...additionalTermoDeAdesaos, ...termoDeAdesaoCollection];
      jest.spyOn(termoDeAdesaoService, 'addTermoDeAdesaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ termoAdesaoContador });
      comp.ngOnInit();

      expect(termoDeAdesaoService.query).toHaveBeenCalled();
      expect(termoDeAdesaoService.addTermoDeAdesaoToCollectionIfMissing).toHaveBeenCalledWith(
        termoDeAdesaoCollection,
        ...additionalTermoDeAdesaos.map(expect.objectContaining),
      );
      expect(comp.termoDeAdesaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const termoAdesaoContador: ITermoAdesaoContador = { id: 456 };
      const contador: IContador = { id: 31228 };
      termoAdesaoContador.contador = contador;
      const termoDeAdesao: ITermoDeAdesao = { id: 5077 };
      termoAdesaoContador.termoDeAdesao = termoDeAdesao;

      activatedRoute.data = of({ termoAdesaoContador });
      comp.ngOnInit();

      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.termoDeAdesaosSharedCollection).toContain(termoDeAdesao);
      expect(comp.termoAdesaoContador).toEqual(termoAdesaoContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoAdesaoContador>>();
      const termoAdesaoContador = { id: 123 };
      jest.spyOn(termoAdesaoContadorFormService, 'getTermoAdesaoContador').mockReturnValue(termoAdesaoContador);
      jest.spyOn(termoAdesaoContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoAdesaoContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoAdesaoContador }));
      saveSubject.complete();

      // THEN
      expect(termoAdesaoContadorFormService.getTermoAdesaoContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(termoAdesaoContadorService.update).toHaveBeenCalledWith(expect.objectContaining(termoAdesaoContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoAdesaoContador>>();
      const termoAdesaoContador = { id: 123 };
      jest.spyOn(termoAdesaoContadorFormService, 'getTermoAdesaoContador').mockReturnValue({ id: null });
      jest.spyOn(termoAdesaoContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoAdesaoContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoAdesaoContador }));
      saveSubject.complete();

      // THEN
      expect(termoAdesaoContadorFormService.getTermoAdesaoContador).toHaveBeenCalled();
      expect(termoAdesaoContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoAdesaoContador>>();
      const termoAdesaoContador = { id: 123 };
      jest.spyOn(termoAdesaoContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoAdesaoContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(termoAdesaoContadorService.update).toHaveBeenCalled();
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

    describe('compareTermoDeAdesao', () => {
      it('Should forward to termoDeAdesaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(termoDeAdesaoService, 'compareTermoDeAdesao');
        comp.compareTermoDeAdesao(entity, entity2);
        expect(termoDeAdesaoService.compareTermoDeAdesao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
