import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ImpostoAPagarEmpresaDetailComponent } from './imposto-a-pagar-empresa-detail.component';

describe('ImpostoAPagarEmpresa Management Detail Component', () => {
  let comp: ImpostoAPagarEmpresaDetailComponent;
  let fixture: ComponentFixture<ImpostoAPagarEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpostoAPagarEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ImpostoAPagarEmpresaDetailComponent,
              resolve: { impostoAPagarEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ImpostoAPagarEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImpostoAPagarEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load impostoAPagarEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ImpostoAPagarEmpresaDetailComponent);

      // THEN
      expect(instance.impostoAPagarEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
