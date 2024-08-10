import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { AreaContabilEmpresaService } from '../service/area-contabil-empresa.service';
import { IAreaContabilEmpresa } from '../area-contabil-empresa.model';
import { AreaContabilEmpresaFormService } from './area-contabil-empresa-form.service';

import { AreaContabilEmpresaUpdateComponent } from './area-contabil-empresa-update.component';

describe('AreaContabilEmpresa Management Update Component', () => {
  let comp: AreaContabilEmpresaUpdateComponent;
  let fixture: ComponentFixture<AreaContabilEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let areaContabilEmpresaFormService: AreaContabilEmpresaFormService;
  let areaContabilEmpresaService: AreaContabilEmpresaService;
  let contadorService: ContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AreaContabilEmpresaUpdateComponent],
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
      .overrideTemplate(AreaContabilEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AreaContabilEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    areaContabilEmpresaFormService = TestBed.inject(AreaContabilEmpresaFormService);
    areaContabilEmpresaService = TestBed.inject(AreaContabilEmpresaService);
    contadorService = TestBed.inject(ContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contador query and add missing value', () => {
      const areaContabilEmpresa: IAreaContabilEmpresa = { id: 456 };
      const contador: IContador = { id: 32687 };
      areaContabilEmpresa.contador = contador;

      const contadorCollection: IContador[] = [{ id: 23211 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ areaContabilEmpresa });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const areaContabilEmpresa: IAreaContabilEmpresa = { id: 456 };
      const contador: IContador = { id: 18784 };
      areaContabilEmpresa.contador = contador;

      activatedRoute.data = of({ areaContabilEmpresa });
      comp.ngOnInit();

      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.areaContabilEmpresa).toEqual(areaContabilEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabilEmpresa>>();
      const areaContabilEmpresa = { id: 123 };
      jest.spyOn(areaContabilEmpresaFormService, 'getAreaContabilEmpresa').mockReturnValue(areaContabilEmpresa);
      jest.spyOn(areaContabilEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabilEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaContabilEmpresa }));
      saveSubject.complete();

      // THEN
      expect(areaContabilEmpresaFormService.getAreaContabilEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(areaContabilEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(areaContabilEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabilEmpresa>>();
      const areaContabilEmpresa = { id: 123 };
      jest.spyOn(areaContabilEmpresaFormService, 'getAreaContabilEmpresa').mockReturnValue({ id: null });
      jest.spyOn(areaContabilEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabilEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaContabilEmpresa }));
      saveSubject.complete();

      // THEN
      expect(areaContabilEmpresaFormService.getAreaContabilEmpresa).toHaveBeenCalled();
      expect(areaContabilEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabilEmpresa>>();
      const areaContabilEmpresa = { id: 123 };
      jest.spyOn(areaContabilEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabilEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(areaContabilEmpresaService.update).toHaveBeenCalled();
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
  });
});
