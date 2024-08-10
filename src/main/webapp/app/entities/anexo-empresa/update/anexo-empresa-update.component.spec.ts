import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { AnexoEmpresaService } from '../service/anexo-empresa.service';
import { IAnexoEmpresa } from '../anexo-empresa.model';
import { AnexoEmpresaFormService } from './anexo-empresa-form.service';

import { AnexoEmpresaUpdateComponent } from './anexo-empresa-update.component';

describe('AnexoEmpresa Management Update Component', () => {
  let comp: AnexoEmpresaUpdateComponent;
  let fixture: ComponentFixture<AnexoEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoEmpresaFormService: AnexoEmpresaFormService;
  let anexoEmpresaService: AnexoEmpresaService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoEmpresaUpdateComponent],
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
      .overrideTemplate(AnexoEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoEmpresaFormService = TestBed.inject(AnexoEmpresaFormService);
    anexoEmpresaService = TestBed.inject(AnexoEmpresaService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const anexoEmpresa: IAnexoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 20745 };
      anexoEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 24027 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoEmpresa: IAnexoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 8571 };
      anexoEmpresa.empresa = empresa;

      activatedRoute.data = of({ anexoEmpresa });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.anexoEmpresa).toEqual(anexoEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoEmpresa>>();
      const anexoEmpresa = { id: 123 };
      jest.spyOn(anexoEmpresaFormService, 'getAnexoEmpresa').mockReturnValue(anexoEmpresa);
      jest.spyOn(anexoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(anexoEmpresaFormService.getAnexoEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(anexoEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoEmpresa>>();
      const anexoEmpresa = { id: 123 };
      jest.spyOn(anexoEmpresaFormService, 'getAnexoEmpresa').mockReturnValue({ id: null });
      jest.spyOn(anexoEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(anexoEmpresaFormService.getAnexoEmpresa).toHaveBeenCalled();
      expect(anexoEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoEmpresa>>();
      const anexoEmpresa = { id: 123 };
      jest.spyOn(anexoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
