import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { OpcaoRazaoSocialEmpresaService } from '../service/opcao-razao-social-empresa.service';
import { IOpcaoRazaoSocialEmpresa } from '../opcao-razao-social-empresa.model';
import { OpcaoRazaoSocialEmpresaFormService } from './opcao-razao-social-empresa-form.service';

import { OpcaoRazaoSocialEmpresaUpdateComponent } from './opcao-razao-social-empresa-update.component';

describe('OpcaoRazaoSocialEmpresa Management Update Component', () => {
  let comp: OpcaoRazaoSocialEmpresaUpdateComponent;
  let fixture: ComponentFixture<OpcaoRazaoSocialEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let opcaoRazaoSocialEmpresaFormService: OpcaoRazaoSocialEmpresaFormService;
  let opcaoRazaoSocialEmpresaService: OpcaoRazaoSocialEmpresaService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OpcaoRazaoSocialEmpresaUpdateComponent],
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
      .overrideTemplate(OpcaoRazaoSocialEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OpcaoRazaoSocialEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    opcaoRazaoSocialEmpresaFormService = TestBed.inject(OpcaoRazaoSocialEmpresaFormService);
    opcaoRazaoSocialEmpresaService = TestBed.inject(OpcaoRazaoSocialEmpresaService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 19662 };
      opcaoRazaoSocialEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 13657 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ opcaoRazaoSocialEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 8279 };
      opcaoRazaoSocialEmpresa.empresa = empresa;

      activatedRoute.data = of({ opcaoRazaoSocialEmpresa });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.opcaoRazaoSocialEmpresa).toEqual(opcaoRazaoSocialEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOpcaoRazaoSocialEmpresa>>();
      const opcaoRazaoSocialEmpresa = { id: 123 };
      jest.spyOn(opcaoRazaoSocialEmpresaFormService, 'getOpcaoRazaoSocialEmpresa').mockReturnValue(opcaoRazaoSocialEmpresa);
      jest.spyOn(opcaoRazaoSocialEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ opcaoRazaoSocialEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: opcaoRazaoSocialEmpresa }));
      saveSubject.complete();

      // THEN
      expect(opcaoRazaoSocialEmpresaFormService.getOpcaoRazaoSocialEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(opcaoRazaoSocialEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(opcaoRazaoSocialEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOpcaoRazaoSocialEmpresa>>();
      const opcaoRazaoSocialEmpresa = { id: 123 };
      jest.spyOn(opcaoRazaoSocialEmpresaFormService, 'getOpcaoRazaoSocialEmpresa').mockReturnValue({ id: null });
      jest.spyOn(opcaoRazaoSocialEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ opcaoRazaoSocialEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: opcaoRazaoSocialEmpresa }));
      saveSubject.complete();

      // THEN
      expect(opcaoRazaoSocialEmpresaFormService.getOpcaoRazaoSocialEmpresa).toHaveBeenCalled();
      expect(opcaoRazaoSocialEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOpcaoRazaoSocialEmpresa>>();
      const opcaoRazaoSocialEmpresa = { id: 123 };
      jest.spyOn(opcaoRazaoSocialEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ opcaoRazaoSocialEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(opcaoRazaoSocialEmpresaService.update).toHaveBeenCalled();
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
