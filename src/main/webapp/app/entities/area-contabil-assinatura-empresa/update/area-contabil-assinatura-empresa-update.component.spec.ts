import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { AreaContabilService } from 'app/entities/area-contabil/service/area-contabil.service';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IAreaContabilAssinaturaEmpresa } from '../area-contabil-assinatura-empresa.model';
import { AreaContabilAssinaturaEmpresaService } from '../service/area-contabil-assinatura-empresa.service';
import { AreaContabilAssinaturaEmpresaFormService } from './area-contabil-assinatura-empresa-form.service';

import { AreaContabilAssinaturaEmpresaUpdateComponent } from './area-contabil-assinatura-empresa-update.component';

describe('AreaContabilAssinaturaEmpresa Management Update Component', () => {
  let comp: AreaContabilAssinaturaEmpresaUpdateComponent;
  let fixture: ComponentFixture<AreaContabilAssinaturaEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let areaContabilAssinaturaEmpresaFormService: AreaContabilAssinaturaEmpresaFormService;
  let areaContabilAssinaturaEmpresaService: AreaContabilAssinaturaEmpresaService;
  let areaContabilService: AreaContabilService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;
  let contadorService: ContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AreaContabilAssinaturaEmpresaUpdateComponent],
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
      .overrideTemplate(AreaContabilAssinaturaEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AreaContabilAssinaturaEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    areaContabilAssinaturaEmpresaFormService = TestBed.inject(AreaContabilAssinaturaEmpresaFormService);
    areaContabilAssinaturaEmpresaService = TestBed.inject(AreaContabilAssinaturaEmpresaService);
    areaContabilService = TestBed.inject(AreaContabilService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);
    contadorService = TestBed.inject(ContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AreaContabil query and add missing value', () => {
      const areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa = { id: 456 };
      const areaContabil: IAreaContabil = { id: 17537 };
      areaContabilAssinaturaEmpresa.areaContabil = areaContabil;

      const areaContabilCollection: IAreaContabil[] = [{ id: 17244 }];
      jest.spyOn(areaContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: areaContabilCollection })));
      const additionalAreaContabils = [areaContabil];
      const expectedCollection: IAreaContabil[] = [...additionalAreaContabils, ...areaContabilCollection];
      jest.spyOn(areaContabilService, 'addAreaContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ areaContabilAssinaturaEmpresa });
      comp.ngOnInit();

      expect(areaContabilService.query).toHaveBeenCalled();
      expect(areaContabilService.addAreaContabilToCollectionIfMissing).toHaveBeenCalledWith(
        areaContabilCollection,
        ...additionalAreaContabils.map(expect.objectContaining),
      );
      expect(comp.areaContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AssinaturaEmpresa query and add missing value', () => {
      const areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 312 };
      areaContabilAssinaturaEmpresa.assinaturaEmpresa = assinaturaEmpresa;

      const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [{ id: 5933 }];
      jest.spyOn(assinaturaEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: assinaturaEmpresaCollection })));
      const additionalAssinaturaEmpresas = [assinaturaEmpresa];
      const expectedCollection: IAssinaturaEmpresa[] = [...additionalAssinaturaEmpresas, ...assinaturaEmpresaCollection];
      jest.spyOn(assinaturaEmpresaService, 'addAssinaturaEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ areaContabilAssinaturaEmpresa });
      comp.ngOnInit();

      expect(assinaturaEmpresaService.query).toHaveBeenCalled();
      expect(assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        assinaturaEmpresaCollection,
        ...additionalAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.assinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Contador query and add missing value', () => {
      const areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa = { id: 456 };
      const contador: IContador = { id: 13382 };
      areaContabilAssinaturaEmpresa.contador = contador;

      const contadorCollection: IContador[] = [{ id: 19649 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ areaContabilAssinaturaEmpresa });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa = { id: 456 };
      const areaContabil: IAreaContabil = { id: 3088 };
      areaContabilAssinaturaEmpresa.areaContabil = areaContabil;
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 13682 };
      areaContabilAssinaturaEmpresa.assinaturaEmpresa = assinaturaEmpresa;
      const contador: IContador = { id: 29620 };
      areaContabilAssinaturaEmpresa.contador = contador;

      activatedRoute.data = of({ areaContabilAssinaturaEmpresa });
      comp.ngOnInit();

      expect(comp.areaContabilsSharedCollection).toContain(areaContabil);
      expect(comp.assinaturaEmpresasSharedCollection).toContain(assinaturaEmpresa);
      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.areaContabilAssinaturaEmpresa).toEqual(areaContabilAssinaturaEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabilAssinaturaEmpresa>>();
      const areaContabilAssinaturaEmpresa = { id: 123 };
      jest
        .spyOn(areaContabilAssinaturaEmpresaFormService, 'getAreaContabilAssinaturaEmpresa')
        .mockReturnValue(areaContabilAssinaturaEmpresa);
      jest.spyOn(areaContabilAssinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabilAssinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaContabilAssinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(areaContabilAssinaturaEmpresaFormService.getAreaContabilAssinaturaEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(areaContabilAssinaturaEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(areaContabilAssinaturaEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabilAssinaturaEmpresa>>();
      const areaContabilAssinaturaEmpresa = { id: 123 };
      jest.spyOn(areaContabilAssinaturaEmpresaFormService, 'getAreaContabilAssinaturaEmpresa').mockReturnValue({ id: null });
      jest.spyOn(areaContabilAssinaturaEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabilAssinaturaEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaContabilAssinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(areaContabilAssinaturaEmpresaFormService.getAreaContabilAssinaturaEmpresa).toHaveBeenCalled();
      expect(areaContabilAssinaturaEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabilAssinaturaEmpresa>>();
      const areaContabilAssinaturaEmpresa = { id: 123 };
      jest.spyOn(areaContabilAssinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabilAssinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(areaContabilAssinaturaEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAreaContabil', () => {
      it('Should forward to areaContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(areaContabilService, 'compareAreaContabil');
        comp.compareAreaContabil(entity, entity2);
        expect(areaContabilService.compareAreaContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAssinaturaEmpresa', () => {
      it('Should forward to assinaturaEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(assinaturaEmpresaService, 'compareAssinaturaEmpresa');
        comp.compareAssinaturaEmpresa(entity, entity2);
        expect(assinaturaEmpresaService.compareAssinaturaEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
