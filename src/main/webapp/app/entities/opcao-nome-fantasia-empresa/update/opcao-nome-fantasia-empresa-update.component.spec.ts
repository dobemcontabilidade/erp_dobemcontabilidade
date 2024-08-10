import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { OpcaoNomeFantasiaEmpresaService } from '../service/opcao-nome-fantasia-empresa.service';
import { IOpcaoNomeFantasiaEmpresa } from '../opcao-nome-fantasia-empresa.model';
import { OpcaoNomeFantasiaEmpresaFormService } from './opcao-nome-fantasia-empresa-form.service';

import { OpcaoNomeFantasiaEmpresaUpdateComponent } from './opcao-nome-fantasia-empresa-update.component';

describe('OpcaoNomeFantasiaEmpresa Management Update Component', () => {
  let comp: OpcaoNomeFantasiaEmpresaUpdateComponent;
  let fixture: ComponentFixture<OpcaoNomeFantasiaEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let opcaoNomeFantasiaEmpresaFormService: OpcaoNomeFantasiaEmpresaFormService;
  let opcaoNomeFantasiaEmpresaService: OpcaoNomeFantasiaEmpresaService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OpcaoNomeFantasiaEmpresaUpdateComponent],
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
      .overrideTemplate(OpcaoNomeFantasiaEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OpcaoNomeFantasiaEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    opcaoNomeFantasiaEmpresaFormService = TestBed.inject(OpcaoNomeFantasiaEmpresaFormService);
    opcaoNomeFantasiaEmpresaService = TestBed.inject(OpcaoNomeFantasiaEmpresaService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 25865 };
      opcaoNomeFantasiaEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 22577 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ opcaoNomeFantasiaEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 22633 };
      opcaoNomeFantasiaEmpresa.empresa = empresa;

      activatedRoute.data = of({ opcaoNomeFantasiaEmpresa });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.opcaoNomeFantasiaEmpresa).toEqual(opcaoNomeFantasiaEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOpcaoNomeFantasiaEmpresa>>();
      const opcaoNomeFantasiaEmpresa = { id: 123 };
      jest.spyOn(opcaoNomeFantasiaEmpresaFormService, 'getOpcaoNomeFantasiaEmpresa').mockReturnValue(opcaoNomeFantasiaEmpresa);
      jest.spyOn(opcaoNomeFantasiaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ opcaoNomeFantasiaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: opcaoNomeFantasiaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(opcaoNomeFantasiaEmpresaFormService.getOpcaoNomeFantasiaEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(opcaoNomeFantasiaEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(opcaoNomeFantasiaEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOpcaoNomeFantasiaEmpresa>>();
      const opcaoNomeFantasiaEmpresa = { id: 123 };
      jest.spyOn(opcaoNomeFantasiaEmpresaFormService, 'getOpcaoNomeFantasiaEmpresa').mockReturnValue({ id: null });
      jest.spyOn(opcaoNomeFantasiaEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ opcaoNomeFantasiaEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: opcaoNomeFantasiaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(opcaoNomeFantasiaEmpresaFormService.getOpcaoNomeFantasiaEmpresa).toHaveBeenCalled();
      expect(opcaoNomeFantasiaEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOpcaoNomeFantasiaEmpresa>>();
      const opcaoNomeFantasiaEmpresa = { id: 123 };
      jest.spyOn(opcaoNomeFantasiaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ opcaoNomeFantasiaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(opcaoNomeFantasiaEmpresaService.update).toHaveBeenCalled();
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
