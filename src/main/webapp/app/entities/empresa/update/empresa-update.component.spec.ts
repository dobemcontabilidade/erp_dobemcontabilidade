import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { SegmentoCnaeService } from 'app/entities/segmento-cnae/service/segmento-cnae.service';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { IEmpresa } from '../empresa.model';
import { EmpresaService } from '../service/empresa.service';
import { EmpresaFormService } from './empresa-form.service';

import { EmpresaUpdateComponent } from './empresa-update.component';

describe('Empresa Management Update Component', () => {
  let comp: EmpresaUpdateComponent;
  let fixture: ComponentFixture<EmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empresaFormService: EmpresaFormService;
  let empresaService: EmpresaService;
  let segmentoCnaeService: SegmentoCnaeService;
  let empresaModeloService: EmpresaModeloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmpresaUpdateComponent],
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
      .overrideTemplate(EmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empresaFormService = TestBed.inject(EmpresaFormService);
    empresaService = TestBed.inject(EmpresaService);
    segmentoCnaeService = TestBed.inject(SegmentoCnaeService);
    empresaModeloService = TestBed.inject(EmpresaModeloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SegmentoCnae query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const segmentoCnaes: ISegmentoCnae[] = [{ id: 10891 }];
      empresa.segmentoCnaes = segmentoCnaes;

      const segmentoCnaeCollection: ISegmentoCnae[] = [{ id: 7079 }];
      jest.spyOn(segmentoCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: segmentoCnaeCollection })));
      const additionalSegmentoCnaes = [...segmentoCnaes];
      const expectedCollection: ISegmentoCnae[] = [...additionalSegmentoCnaes, ...segmentoCnaeCollection];
      jest.spyOn(segmentoCnaeService, 'addSegmentoCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(segmentoCnaeService.query).toHaveBeenCalled();
      expect(segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        segmentoCnaeCollection,
        ...additionalSegmentoCnaes.map(expect.objectContaining),
      );
      expect(comp.segmentoCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EmpresaModelo query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const empresaModelo: IEmpresaModelo = { id: 27171 };
      empresa.empresaModelo = empresaModelo;

      const empresaModeloCollection: IEmpresaModelo[] = [{ id: 22590 }];
      jest.spyOn(empresaModeloService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaModeloCollection })));
      const additionalEmpresaModelos = [empresaModelo];
      const expectedCollection: IEmpresaModelo[] = [...additionalEmpresaModelos, ...empresaModeloCollection];
      jest.spyOn(empresaModeloService, 'addEmpresaModeloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(empresaModeloService.query).toHaveBeenCalled();
      expect(empresaModeloService.addEmpresaModeloToCollectionIfMissing).toHaveBeenCalledWith(
        empresaModeloCollection,
        ...additionalEmpresaModelos.map(expect.objectContaining),
      );
      expect(comp.empresaModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const empresa: IEmpresa = { id: 456 };
      const segmentoCnae: ISegmentoCnae = { id: 9455 };
      empresa.segmentoCnaes = [segmentoCnae];
      const empresaModelo: IEmpresaModelo = { id: 13327 };
      empresa.empresaModelo = empresaModelo;

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(comp.segmentoCnaesSharedCollection).toContain(segmentoCnae);
      expect(comp.empresaModelosSharedCollection).toContain(empresaModelo);
      expect(comp.empresa).toEqual(empresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaFormService, 'getEmpresa').mockReturnValue(empresa);
      jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresa }));
      saveSubject.complete();

      // THEN
      expect(empresaFormService.getEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empresaService.update).toHaveBeenCalledWith(expect.objectContaining(empresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaFormService, 'getEmpresa').mockReturnValue({ id: null });
      jest.spyOn(empresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresa }));
      saveSubject.complete();

      // THEN
      expect(empresaFormService.getEmpresa).toHaveBeenCalled();
      expect(empresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSegmentoCnae', () => {
      it('Should forward to segmentoCnaeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(segmentoCnaeService, 'compareSegmentoCnae');
        comp.compareSegmentoCnae(entity, entity2);
        expect(segmentoCnaeService.compareSegmentoCnae).toHaveBeenCalledWith(entity, entity2);
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
