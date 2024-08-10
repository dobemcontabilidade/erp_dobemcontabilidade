import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IBanco } from 'app/entities/banco/banco.model';
import { BancoService } from 'app/entities/banco/service/banco.service';
import { IBancoContador } from '../banco-contador.model';
import { BancoContadorService } from '../service/banco-contador.service';
import { BancoContadorFormService } from './banco-contador-form.service';

import { BancoContadorUpdateComponent } from './banco-contador-update.component';

describe('BancoContador Management Update Component', () => {
  let comp: BancoContadorUpdateComponent;
  let fixture: ComponentFixture<BancoContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bancoContadorFormService: BancoContadorFormService;
  let bancoContadorService: BancoContadorService;
  let contadorService: ContadorService;
  let bancoService: BancoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [BancoContadorUpdateComponent],
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
      .overrideTemplate(BancoContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BancoContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bancoContadorFormService = TestBed.inject(BancoContadorFormService);
    bancoContadorService = TestBed.inject(BancoContadorService);
    contadorService = TestBed.inject(ContadorService);
    bancoService = TestBed.inject(BancoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contador query and add missing value', () => {
      const bancoContador: IBancoContador = { id: 456 };
      const contador: IContador = { id: 15647 };
      bancoContador.contador = contador;

      const contadorCollection: IContador[] = [{ id: 17764 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bancoContador });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Banco query and add missing value', () => {
      const bancoContador: IBancoContador = { id: 456 };
      const banco: IBanco = { id: 256 };
      bancoContador.banco = banco;

      const bancoCollection: IBanco[] = [{ id: 24818 }];
      jest.spyOn(bancoService, 'query').mockReturnValue(of(new HttpResponse({ body: bancoCollection })));
      const additionalBancos = [banco];
      const expectedCollection: IBanco[] = [...additionalBancos, ...bancoCollection];
      jest.spyOn(bancoService, 'addBancoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bancoContador });
      comp.ngOnInit();

      expect(bancoService.query).toHaveBeenCalled();
      expect(bancoService.addBancoToCollectionIfMissing).toHaveBeenCalledWith(
        bancoCollection,
        ...additionalBancos.map(expect.objectContaining),
      );
      expect(comp.bancosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bancoContador: IBancoContador = { id: 456 };
      const contador: IContador = { id: 30859 };
      bancoContador.contador = contador;
      const banco: IBanco = { id: 8795 };
      bancoContador.banco = banco;

      activatedRoute.data = of({ bancoContador });
      comp.ngOnInit();

      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.bancosSharedCollection).toContain(banco);
      expect(comp.bancoContador).toEqual(bancoContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBancoContador>>();
      const bancoContador = { id: 123 };
      jest.spyOn(bancoContadorFormService, 'getBancoContador').mockReturnValue(bancoContador);
      jest.spyOn(bancoContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bancoContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bancoContador }));
      saveSubject.complete();

      // THEN
      expect(bancoContadorFormService.getBancoContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bancoContadorService.update).toHaveBeenCalledWith(expect.objectContaining(bancoContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBancoContador>>();
      const bancoContador = { id: 123 };
      jest.spyOn(bancoContadorFormService, 'getBancoContador').mockReturnValue({ id: null });
      jest.spyOn(bancoContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bancoContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bancoContador }));
      saveSubject.complete();

      // THEN
      expect(bancoContadorFormService.getBancoContador).toHaveBeenCalled();
      expect(bancoContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBancoContador>>();
      const bancoContador = { id: 123 };
      jest.spyOn(bancoContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bancoContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bancoContadorService.update).toHaveBeenCalled();
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

    describe('compareBanco', () => {
      it('Should forward to bancoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(bancoService, 'compareBanco');
        comp.compareBanco(entity, entity2);
        expect(bancoService.compareBanco).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
