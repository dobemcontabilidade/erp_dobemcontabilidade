import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OpcaoRazaoSocialEmpresaDetailComponent } from './opcao-razao-social-empresa-detail.component';

describe('OpcaoRazaoSocialEmpresa Management Detail Component', () => {
  let comp: OpcaoRazaoSocialEmpresaDetailComponent;
  let fixture: ComponentFixture<OpcaoRazaoSocialEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OpcaoRazaoSocialEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: OpcaoRazaoSocialEmpresaDetailComponent,
              resolve: { opcaoRazaoSocialEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OpcaoRazaoSocialEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OpcaoRazaoSocialEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load opcaoRazaoSocialEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OpcaoRazaoSocialEmpresaDetailComponent);

      // THEN
      expect(instance.opcaoRazaoSocialEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
