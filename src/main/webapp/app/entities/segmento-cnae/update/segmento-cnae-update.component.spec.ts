import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ISubclasseCnae } from 'app/entities/subclasse-cnae/subclasse-cnae.model';
import { SubclasseCnaeService } from 'app/entities/subclasse-cnae/service/subclasse-cnae.service';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { ISegmentoCnae } from '../segmento-cnae.model';
import { SegmentoCnaeService } from '../service/segmento-cnae.service';
import { SegmentoCnaeFormService } from './segmento-cnae-form.service';

import { SegmentoCnaeUpdateComponent } from './segmento-cnae-update.component';

describe('SegmentoCnae Management Update Component', () => {
  let comp: SegmentoCnaeUpdateComponent;
  let fixture: ComponentFixture<SegmentoCnaeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let segmentoCnaeFormService: SegmentoCnaeFormService;
  let segmentoCnaeService: SegmentoCnaeService;
  let subclasseCnaeService: SubclasseCnaeService;
  let ramoService: RamoService;
  let empresaService: EmpresaService;
  let empresaModeloService: EmpresaModeloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SegmentoCnaeUpdateComponent],
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
      .overrideTemplate(SegmentoCnaeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SegmentoCnaeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    segmentoCnaeFormService = TestBed.inject(SegmentoCnaeFormService);
    segmentoCnaeService = TestBed.inject(SegmentoCnaeService);
    subclasseCnaeService = TestBed.inject(SubclasseCnaeService);
    ramoService = TestBed.inject(RamoService);
    empresaService = TestBed.inject(EmpresaService);
    empresaModeloService = TestBed.inject(EmpresaModeloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SubclasseCnae query and add missing value', () => {
      const segmentoCnae: ISegmentoCnae = { id: 456 };
      const subclasseCnaes: ISubclasseCnae[] = [{ id: 23600 }];
      segmentoCnae.subclasseCnaes = subclasseCnaes;

      const subclasseCnaeCollection: ISubclasseCnae[] = [{ id: 32446 }];
      jest.spyOn(subclasseCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: subclasseCnaeCollection })));
      const additionalSubclasseCnaes = [...subclasseCnaes];
      const expectedCollection: ISubclasseCnae[] = [...additionalSubclasseCnaes, ...subclasseCnaeCollection];
      jest.spyOn(subclasseCnaeService, 'addSubclasseCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ segmentoCnae });
      comp.ngOnInit();

      expect(subclasseCnaeService.query).toHaveBeenCalled();
      expect(subclasseCnaeService.addSubclasseCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        subclasseCnaeCollection,
        ...additionalSubclasseCnaes.map(expect.objectContaining),
      );
      expect(comp.subclasseCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ramo query and add missing value', () => {
      const segmentoCnae: ISegmentoCnae = { id: 456 };
      const ramo: IRamo = { id: 1206 };
      segmentoCnae.ramo = ramo;

      const ramoCollection: IRamo[] = [{ id: 15671 }];
      jest.spyOn(ramoService, 'query').mockReturnValue(of(new HttpResponse({ body: ramoCollection })));
      const additionalRamos = [ramo];
      const expectedCollection: IRamo[] = [...additionalRamos, ...ramoCollection];
      jest.spyOn(ramoService, 'addRamoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ segmentoCnae });
      comp.ngOnInit();

      expect(ramoService.query).toHaveBeenCalled();
      expect(ramoService.addRamoToCollectionIfMissing).toHaveBeenCalledWith(
        ramoCollection,
        ...additionalRamos.map(expect.objectContaining),
      );
      expect(comp.ramosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const segmentoCnae: ISegmentoCnae = { id: 456 };
      const empresas: IEmpresa[] = [{ id: 17464 }];
      segmentoCnae.empresas = empresas;

      const empresaCollection: IEmpresa[] = [{ id: 21580 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [...empresas];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ segmentoCnae });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EmpresaModelo query and add missing value', () => {
      const segmentoCnae: ISegmentoCnae = { id: 456 };
      const empresaModelos: IEmpresaModelo[] = [{ id: 15864 }];
      segmentoCnae.empresaModelos = empresaModelos;

      const empresaModeloCollection: IEmpresaModelo[] = [{ id: 29608 }];
      jest.spyOn(empresaModeloService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaModeloCollection })));
      const additionalEmpresaModelos = [...empresaModelos];
      const expectedCollection: IEmpresaModelo[] = [...additionalEmpresaModelos, ...empresaModeloCollection];
      jest.spyOn(empresaModeloService, 'addEmpresaModeloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ segmentoCnae });
      comp.ngOnInit();

      expect(empresaModeloService.query).toHaveBeenCalled();
      expect(empresaModeloService.addEmpresaModeloToCollectionIfMissing).toHaveBeenCalledWith(
        empresaModeloCollection,
        ...additionalEmpresaModelos.map(expect.objectContaining),
      );
      expect(comp.empresaModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const segmentoCnae: ISegmentoCnae = { id: 456 };
      const subclasseCnae: ISubclasseCnae = { id: 1491 };
      segmentoCnae.subclasseCnaes = [subclasseCnae];
      const ramo: IRamo = { id: 18211 };
      segmentoCnae.ramo = ramo;
      const empresa: IEmpresa = { id: 17273 };
      segmentoCnae.empresas = [empresa];
      const empresaModelo: IEmpresaModelo = { id: 26081 };
      segmentoCnae.empresaModelos = [empresaModelo];

      activatedRoute.data = of({ segmentoCnae });
      comp.ngOnInit();

      expect(comp.subclasseCnaesSharedCollection).toContain(subclasseCnae);
      expect(comp.ramosSharedCollection).toContain(ramo);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.empresaModelosSharedCollection).toContain(empresaModelo);
      expect(comp.segmentoCnae).toEqual(segmentoCnae);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISegmentoCnae>>();
      const segmentoCnae = { id: 123 };
      jest.spyOn(segmentoCnaeFormService, 'getSegmentoCnae').mockReturnValue(segmentoCnae);
      jest.spyOn(segmentoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ segmentoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: segmentoCnae }));
      saveSubject.complete();

      // THEN
      expect(segmentoCnaeFormService.getSegmentoCnae).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(segmentoCnaeService.update).toHaveBeenCalledWith(expect.objectContaining(segmentoCnae));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISegmentoCnae>>();
      const segmentoCnae = { id: 123 };
      jest.spyOn(segmentoCnaeFormService, 'getSegmentoCnae').mockReturnValue({ id: null });
      jest.spyOn(segmentoCnaeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ segmentoCnae: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: segmentoCnae }));
      saveSubject.complete();

      // THEN
      expect(segmentoCnaeFormService.getSegmentoCnae).toHaveBeenCalled();
      expect(segmentoCnaeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISegmentoCnae>>();
      const segmentoCnae = { id: 123 };
      jest.spyOn(segmentoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ segmentoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(segmentoCnaeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSubclasseCnae', () => {
      it('Should forward to subclasseCnaeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(subclasseCnaeService, 'compareSubclasseCnae');
        comp.compareSubclasseCnae(entity, entity2);
        expect(subclasseCnaeService.compareSubclasseCnae).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRamo', () => {
      it('Should forward to ramoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ramoService, 'compareRamo');
        comp.compareRamo(entity, entity2);
        expect(ramoService.compareRamo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmpresaModelo', () => {
      it('Should forward to empresaModeloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaModeloService, 'compareEmpresaModelo');
        comp.compareEmpresaModelo(entity, entity2);
        expect(empresaModeloService.compareEmpresaModelo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
