import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAdministrador } from 'app/entities/administrador/administrador.model';
import { AdministradorService } from 'app/entities/administrador/service/administrador.service';
import { UsuarioErpService } from '../service/usuario-erp.service';
import { IUsuarioErp } from '../usuario-erp.model';
import { UsuarioErpFormService } from './usuario-erp-form.service';

import { UsuarioErpUpdateComponent } from './usuario-erp-update.component';

describe('UsuarioErp Management Update Component', () => {
  let comp: UsuarioErpUpdateComponent;
  let fixture: ComponentFixture<UsuarioErpUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let usuarioErpFormService: UsuarioErpFormService;
  let usuarioErpService: UsuarioErpService;
  let administradorService: AdministradorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UsuarioErpUpdateComponent],
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
      .overrideTemplate(UsuarioErpUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UsuarioErpUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    usuarioErpFormService = TestBed.inject(UsuarioErpFormService);
    usuarioErpService = TestBed.inject(UsuarioErpService);
    administradorService = TestBed.inject(AdministradorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call administrador query and add missing value', () => {
      const usuarioErp: IUsuarioErp = { id: 456 };
      const administrador: IAdministrador = { id: 1340 };
      usuarioErp.administrador = administrador;

      const administradorCollection: IAdministrador[] = [{ id: 22319 }];
      jest.spyOn(administradorService, 'query').mockReturnValue(of(new HttpResponse({ body: administradorCollection })));
      const expectedCollection: IAdministrador[] = [administrador, ...administradorCollection];
      jest.spyOn(administradorService, 'addAdministradorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuarioErp });
      comp.ngOnInit();

      expect(administradorService.query).toHaveBeenCalled();
      expect(administradorService.addAdministradorToCollectionIfMissing).toHaveBeenCalledWith(administradorCollection, administrador);
      expect(comp.administradorsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const usuarioErp: IUsuarioErp = { id: 456 };
      const administrador: IAdministrador = { id: 30324 };
      usuarioErp.administrador = administrador;

      activatedRoute.data = of({ usuarioErp });
      comp.ngOnInit();

      expect(comp.administradorsCollection).toContain(administrador);
      expect(comp.usuarioErp).toEqual(usuarioErp);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioErp>>();
      const usuarioErp = { id: 123 };
      jest.spyOn(usuarioErpFormService, 'getUsuarioErp').mockReturnValue(usuarioErp);
      jest.spyOn(usuarioErpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioErp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioErp }));
      saveSubject.complete();

      // THEN
      expect(usuarioErpFormService.getUsuarioErp).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(usuarioErpService.update).toHaveBeenCalledWith(expect.objectContaining(usuarioErp));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioErp>>();
      const usuarioErp = { id: 123 };
      jest.spyOn(usuarioErpFormService, 'getUsuarioErp').mockReturnValue({ id: null });
      jest.spyOn(usuarioErpService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioErp: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioErp }));
      saveSubject.complete();

      // THEN
      expect(usuarioErpFormService.getUsuarioErp).toHaveBeenCalled();
      expect(usuarioErpService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioErp>>();
      const usuarioErp = { id: 123 };
      jest.spyOn(usuarioErpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioErp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(usuarioErpService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAdministrador', () => {
      it('Should forward to administradorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(administradorService, 'compareAdministrador');
        comp.compareAdministrador(entity, entity2);
        expect(administradorService.compareAdministrador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
